import { useNavigate } from "react-router-dom";
import { Container } from "../Container/Container";
import { useState } from "react";
import s from "./Home.module.css"

export const Home = () => {
  const [query, setQuery] = useState("");
  const navigate = useNavigate();

  const handleChange = (event) => {
    setQuery(event.target.value);
  };

  const handleKeyPress = (event) => {
    if (event.key === "Enter") navigate(`player/${event.target.value}`);
  };

  return (
    <section className={s.statsSection}>
      <div className={s.container}>
        <h1 className={s.title}>Проверь игровую статистику</h1>

        <div className={s.inputWrapper}>
          <input
            type="text"
            className={s.searchInput}
            value={query}
            placeholder="Ник игрока... Fozeton"
            onChange={handleChange}
            onKeyDown={handleKeyPress}
          />
        </div>
      </div>
    </section>
  );
};
