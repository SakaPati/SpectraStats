import { Route, Routes } from "react-router-dom";
import { Home } from "./components/Home/Home";
import { Main } from "./components/Main/Main";
import { Statistic } from "./components/Statistic/Statistic";
import { StatisticDetail } from "./components/Statistic/StatisticDetail";
import { NotFound } from "./components/Statistic/NotFound";

function App() {
  return (
    <>
      <Main>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/player/:player" element={<Statistic />} />
          <Route path="/player/:player/:type" element={<StatisticDetail />} />
          <Route path="/player/:player/notFound" element={<NotFound />} />
        </Routes>
      </Main>
    </>
  );
}

export default App;
