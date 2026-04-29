import { Route, Routes } from "react-router-dom";
import { Home } from "./components/Home/Home";
import { Main } from "./components/Main/Main";
import { Statistic } from "./components/Statistic/Statistic";
import { StatisticDetail } from "./components/Statistic/StatisticDetail";

function App() {
  return (
    <>
      <Main>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/player/:player" element={<Statistic />} />
          <Route path="/player/:player/:type" element={<StatisticDetail />} />
        </Routes>
      </Main>
    </>
  );
}

export default App;
