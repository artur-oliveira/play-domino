import {useEffect, useState} from "react";
import {HiX} from "react-icons/hi";
import {WalletResponse} from "../../../models/wallet.models.ts";
import {UseQueryResult} from "@tanstack/react-query";
import {toast} from "sonner";
import GenericModalComponent from "../../generic/GenericModalComponent.tsx";

interface Props {
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (amount: number) => void;
    wallet: UseQueryResult<WalletResponse, Error>;
}

export default function NewGameModal({isOpen, onClose, onConfirm, wallet}: Props) {
    const predefined = [0, 2, 5, 10, 20];
    const [selected, setSelected] = useState<number | null>(null);
    const [customValue, setCustomValue] = useState("");

    useEffect(() => {
        if (selected !== null && selected !== -1) {
            setCustomValue("");
        }
    }, [selected]);

    const parsedCustom = parseFloat(customValue.replace(",", "."));

    const selectedValue = selected === -1 ? parsedCustom : selected;

    if (wallet.isPending || wallet.isError) {
        toast.error('Saldo da carteira não foi carregado!');
        return null;
    }
    const balance = wallet.data.availableCents / 100;
    const isCustomValid = !isNaN(parsedCustom) && parsedCustom > 0 && parsedCustom <= balance;

    return (
        <GenericModalComponent
            isOpen={isOpen}
            onClose={onClose}
            child={
                <>
                    <button
                        onClick={onClose}
                        className="absolute top-3 right-3 text-[#fdeccd] hover:text-red-400 transition"
                    >
                        <HiX size={20}/>
                    </button>

                    <h2 className="text-xl font-bold mb-2">Nova Partida</h2>
                    <p className="text-sm mb-6">Saldo
                        disponível: <strong>{wallet.data.displayAvailableCents}</strong></p>

                    <div className="grid grid-cols-2 gap-3 mb-4">
                        {predefined.map(value => (
                            <button
                                key={value}
                                className={`py-2 px-3 rounded-lg border font-medium transition ${
                                    selected === value
                                        ? 'bg-[#fdeccd] text-zinc-900 border-[#fdeccd]'
                                        : 'border-zinc-500 hover:bg-zinc-800'
                                }`}
                                onClick={() => setSelected(value)}
                            >
                                {value === 0 ? 'Sem aposta' : `R$ ${value}`}
                            </button>
                        ))}
                        <button
                            className={`py-2 px-3 rounded-lg border font-medium transition ${
                                selected === -1
                                    ? 'bg-[#fdeccd] text-zinc-900 border-[#fdeccd]'
                                    : 'border-zinc-500 hover:bg-zinc-800'
                            }`}
                            onClick={() => setSelected(-1)}
                        >
                            Personalizado
                        </button>
                    </div>

                    {selected === -1 && (
                        <input
                            type="number"
                            step="0.01"
                            inputMode="decimal"
                            placeholder="Digite o valor da aposta"
                            className="w-full mb-4 py-2 px-3 rounded-lg bg-zinc-800 border border-zinc-600 text-white placeholder-zinc-400 focus:outline-none focus:ring focus:ring-[#fdeccd]/30"
                            value={customValue}
                            onChange={(e) => setCustomValue(e.target.value)}
                            onKeyDown={(e) => {
                                if (["e", "E", "+", "-"].includes(e.key)) {
                                    e.preventDefault();
                                }
                            }}
                            onPaste={(e) => {
                                const paste = e.clipboardData.getData("text");
                                if (!/^\d*\.?\d*$/.test(paste)) {
                                    e.preventDefault();
                                }
                            }}
                        />
                    )}

                    <button
                        onClick={() => selectedValue !== null && selectedValue <= balance && onConfirm(selectedValue)}
                        disabled={selectedValue === null || selectedValue > balance || (selected === -1 && !isCustomValid)}
                        className="bg-[#fdeccd] text-zinc-900 py-2 px-4 w-full rounded-lg font-semibold hover:opacity-90 transition disabled:opacity-40"
                    >
                        Confirmar
                    </button>
                </>
            }/>
    );
}
