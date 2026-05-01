import { useNavigate } from "react-router-dom";
import { Container } from "../Container/Container";
import { useRef, useState } from "react";
import s from "./Home.module.css";
import { useDebouncedCallback } from "use-debounce";
import axios from "axios";
import { Stats } from "../assets/stats";

export const Home = () => {
  const [query, setQuery] = useState("");
  const [suggest, setSuggest] = useState(null);
  const searchRef = useRef(null);
  const navigate = useNavigate();

  const debounce = useDebouncedCallback((value) => {
    try {
      const handleSuggest = async () => {
        if (value.length > 0) {
          const result = Stats(value) || (await axios.get(`/api/player/suggest/${value}`));
          console.log(result);

          setSuggest(result.data);
        } else setSuggest([]);
      };
      handleSuggest();
    } catch (error) {}
  }, 500);

  const handleChange = (event) => {
    const value = event.target.value;
    setQuery(value);
    debounce(value);
  };

  const handleClickPlayer = (value) => {
    setQuery(value);
    searchRef.current.focus();
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
            placeholder="Ник игрока... Foz"
            onChange={handleChange}
            onKeyDown={handleKeyPress}
            ref={searchRef}
          />

          {suggest && suggest.length > 0 && (
            <ul className={s.suggestList}>
              {suggest.map((value) => (
                <li key={value} className={s.suggestItem} onClick={() => handleClickPlayer(value)}>
                  <p>{value}</p>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </section>
  );
};
