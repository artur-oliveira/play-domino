import {useState} from 'react';
import {useGetAllFaq} from "../../api/faq.api.ts";
import {Skeleton} from "../generic/Skeleton.tsx";

const FAQ = () => {
    const [activeIndex, setActiveIndex] = useState<number | null>(null);
    const {data: faqs, isLoading} = useGetAllFaq();

    const toggleQuestion = (index: number) => {
        setActiveIndex(activeIndex === index ? null : index); // Alterna entre expandir e colapsar
    };

    if (isLoading) {
        return (
            <section className="py-16 bg-zinc-900 text-[#fdeccd] rounded-xl mt-8  px-4 w-full max-w-7xl mx-auto">
                <div className="max-w-7xl mx-auto px-6 text-center">
                    <h2 className="text-3xl font-bold mb-8">Perguntas Frequentes</h2>
                    <div className="space-y-4">
                        <Skeleton className="w-full rounded-t-xl h-16"/>
                        <Skeleton className="w-full rounded-t-xl h-16"/>
                        <Skeleton className="w-full rounded-t-xl h-16"/>
                        <Skeleton className="w-full rounded-t-xl h-16"/>
                    </div>
                </div>
            </section>
        )
    }

    console.log(faqs?.data);

    return (
        <section className="py-16 bg-zinc-900 text-[#fdeccd] rounded-xl mt-8  px-4 w-full max-w-7xl mx-auto">
            <div className="max-w-7xl mx-auto px-6 text-center">
                <h2 className="text-3xl font-bold mb-8">Perguntas Frequentes</h2>
                {isLoading && (
                    <div className="space-y-4">
                        <Skeleton className="w-full rounded-t-xl h-16"/>
                        <Skeleton className="w-full rounded-t-xl h-16"/>
                        <Skeleton className="w-full rounded-t-xl h-16"/>
                        <Skeleton className="w-full rounded-t-xl h-16"/>
                    </div>
                )}
                {!isLoading && (
                    <div className="space-y-4">
                        {(faqs?.data || []).map((faq, index) => (
                            <div
                                key={index}
                                className="border-b border-zinc-700"
                            >
                                <div
                                    onClick={() => toggleQuestion(index)}
                                    className="flex justify-between items-center p-4 cursor-pointer hover:bg-zinc-800 transition rounded-t-xl"
                                >
                                    <h3 className="text-lg font-semibold">{faq.question}</h3>
                                    <span
                                        className={`transform transition ${activeIndex === index ? 'rotate-180' : ''}`}>
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
                )}
            </div>
        </section>
    );
};

export default FAQ;
