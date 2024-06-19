import NavBar from "./components/NavBar/NavBar";
import Footer from "./components/Footer/Footer";
import Header from "./components/Header/Header";
import Artifact from "./components/Artifact/Novel";

function App() {
  return (
    <div>
      <NavBar />
      <div>
        <Header />
        <Artifact />
      </div>
      <Footer />
    </div>
  );
}

export default App;
