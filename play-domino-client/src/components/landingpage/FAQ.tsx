import {useState} from 'react';

const FAQ = () => {
    const [activeIndex, setActiveIndex] = useState<number | null>(null);

    const toggleQuestion = (index: number) => {
        setActiveIndex(activeIndex === index ? null : index); // Alterna entre expandir e colapsar
    };

    const faqs = [
        {
            question: "Preciso apostar pra jogar?",
            answer: "Não, você pode jogar gratuitamente, mas também temos a opção de apostas se você quiser se divertir e ganhar prêmios!"
        },
        {
            question: "Qual a taxa de saque?",
            answer: "A taxa de saque é de 3%, o que ajuda a manter a plataforma segura e funcionando sem interrupções."
        },
        {
            question: "Tem como jogar com amigos?",
            answer: "Sim! Você pode convidar seus amigos para criar salas privadas e jogar juntos. Apenas envie o código da sala!"
        },
        {
            question: "Como funciona o embaralhamento das peças?",
            answer: "As peças são embaralhadas de forma completamente aleatória. Não se preocupe, sempre será uma experiência justa!"
        },
    ];

    return (
        <section className="py-16 bg-zinc-900 text-[#fdeccd] rounded-xl mt-8  px-4 w-full max-w-7xl mx-auto">
            <div className="max-w-7xl mx-auto px-6 text-center">
                <h2 className="text-3xl font-bold mb-8">Perguntas Frequentes</h2>
                <div className="space-y-4">
                    {faqs.map((faq, index) => (
                        <div
                            key={index}
                            className="border-b border-zinc-700"
                        >
                            <div
                                onClick={() => toggleQuestion(index)}
                                className="flex justify-between items-center p-4 cursor-pointer hover:bg-zinc-800 transition rounded-t-xl"
                            >
                                <h3 className="text-lg font-semibold">{faq.question}</h3>
                                <span className={`transform transition ${activeIndex === index ? 'rotate-180' : ''}`}>
                  <svg className="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                       stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7"/>
                  </svg>
                </span>
                            </div>
                            {activeIndex === index && (
                                <div className="p-4 bg-zinc-800 rounded-b-xl">
                                    <p>{faq.answer}</p>
                                </div>
                            )}
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default FAQ;
