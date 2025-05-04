import FAQ from "../components/landingpage/FAQ.tsx";
import TopBar from "../components/landingpage/TopBar.tsx";
import Hero from "../components/landingpage/Hero.tsx";
import Footer from "../components/landingpage/Footer.tsx";
import Features from "../components/landingpage/Features.tsx";

const LandingPage = () => {
    return (
        <div className="min-h-screen flex flex-col">
            {/* Top Bar */}
            <TopBar/>
            {/* Hero Section */}
            <Hero/>
            {/* Features Section */}
            <Features/>
            {/* FAQ */}
            <FAQ/>
            {/* Footer */}
            <Footer/>
        </div>
    );
}

export default LandingPage;