import { useEffect, useState } from "react";
import { Container } from "../Container/Container";
import axios from "axios";
import { useParams } from "react-router-dom";

export const StatisticDetail = () => {
  const [stats, setStats] = useState(null);
  let param = useParams();

  useEffect(() => {
    const handlerStats = async () => {
      try {
        const result = await axios.get(`/api/statistic/${param.player}/${param.type}`);
        setStats(result.data);
      } catch (error) {
        console.error(error);
      }
    };

    handlerStats();
  }, [param.type]);

  return (
    <section>
      <Container>
        <h1>{ `${param.type.toUpperCase()} Статистика игрока ${param.player}`}</h1>
      </Container>
    </section>
  );
};
