import {FaFacebook, FaInstagram, FaTwitter} from "react-icons/fa6";

export default function Footer() {
    return (
        <footer
            className="bg-zinc-900 text-[#fdeccd] py-10 mt-8 border-t border-zinc-700 px-4 w-full max-w-7xl mx-auto">
            <div className="max-w-7xl mx-auto px-4">
                <div className="grid md:grid-cols-3 gap-10">
                    {/* Coluna 1: Sobre o jogo */}
                    <div>
                        <h4 className="text-2xl font-semibold mb-4">Sobre o PlayDomino</h4>
                        <p className="text-sm">
                            O PlayDomino oferece uma experiência única de jogar dominó online, com apostas seguras,
                            partidas rápidas e justas.
                        </p>
                    </div>

                    {/* Coluna 2: Links úteis */}
                    <div>
                        <h4 className="text-2xl font-semibold mb-4">Links Úteis</h4>
                        <ul className="space-y-2">
                            <li><a href="#" className="hover:text-[#fdeccd] hover:underline transition-all">Sobre</a>
                            </li>
                            <li><a href="#" className="hover:text-[#fdeccd] hover:underline transition-all">Contato</a>
                            </li>
                            <li><a href="#" className="hover:text-[#fdeccd] hover:underline transition-all">Política de
                                Privacidade</a></li>
                            <li><a href="#" className="hover:text-[#fdeccd] hover:underline transition-all">Termos de
                                Uso</a></li>
                        </ul>
                    </div>

                    {/* Coluna 3: Redes sociais */}
                    <div>
                        <h4 className="text-2xl font-semibold mb-4">Siga-nos</h4>
                        <div className="flex gap-6 justify-center md:justify-start">
                            <a href="https://twitter.com/playdomino" target="_blank" rel="noopener noreferrer"
                               className="hover:text-[#1DA1F2] transition-all">
                                <FaTwitter className="inline-block text-2xl"/>
                            </a>
                            <a href="https://facebook.com/playdomino" target="_blank" rel="noopener noreferrer"
                               className="hover:text-[#1877F2] transition-all">
                                <FaFacebook className="inline-block text-2xl"/>
                            </a>
                            <a href="https://instagram.com/playdomino" target="_blank" rel="noopener noreferrer"
                               className="hover:text-[#E4405F] transition-all">
                                <FaInstagram className="inline-block text-2xl"/>
                            </a>
                        </div>
                    </div>
                </div>

                {/* Copyright */}
                <div className="mt-8 text-center">
                    <p className="text-sm">© {new Date().getFullYear()} PlayDomino. Todos os direitos reservados.</p>
                </div>
            </div>
        </footer>
    )
}