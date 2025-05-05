import CheckCurrentGameComponent from "../../components/app/game/CheckCurrentGameComponent.tsx";

const DominoAppPage = () => {
    return (
        <div
            className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center">
            <div className="p-4">
                <CheckCurrentGameComponent/>
            </div>
        </div>
    );
}

export default DominoAppPage;