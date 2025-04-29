export function formatCurrency(value: string) {
    const cleaned = value.replace(/\D/g, "");
    const numberValue = parseFloat(cleaned) / 100;
    if (isNaN(numberValue)) return "";
    return `R$ ${numberValue.toLocaleString("pt-BR", {minimumFractionDigits: 2, maximumFractionDigits: 2})}`;
}