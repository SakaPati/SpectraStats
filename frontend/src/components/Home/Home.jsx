import { useNavigate } from "react-router-dom";
import { Container } from "../Container/Container"
import { useState } from "react";

export const Home = () => {
    const [query, setQuery] = useState('');
    const navigate = useNavigate();

    const handleChange = (event) => { 
        setQuery(event.target.value)
    }

    const handleKeyPress = (event) => { 
        if (event.key === "Enter") navigate(`player/${event.target.value}`)
    }

    return (
      <section>
        <Container>
                <h1>Проверь статиску игровую статистику игрока!</h1>
                <input type="text" value={query} placeholder="Ник игрока" onChange={handleChange} onKeyDown={handleKeyPress}/>
        </Container>
      </section>
    );
}