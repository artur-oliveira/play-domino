
const NewsBannerComponent = () => {
    const news = "Nova versão 1.2 lançada — Confira o novo modo POINTS!";

    return (
        <div className="bg-yellow-500 text-zinc-900 p-3 rounded text-center font-semibold">
            {news}
        </div>
    );
};

export default NewsBannerComponent;
