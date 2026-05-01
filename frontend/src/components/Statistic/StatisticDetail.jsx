import { useEffect, useState } from "react";
import { Container } from "../Container/Container";
import axios from "axios";
import { useParams } from "react-router-dom";
import ru from "../assets/ru_ru.json";
import category from "../assets/ru_category.json";
import s from "./StatisticDetail.module.css";
import { Stats } from "../assets/stats";

export const StatisticDetail = () => {
  const [stats, setStats] = useState(null);
  let params = useParams();

  useEffect(() => {
    const handlerStats = async () => {
      try {
        const result = Stats(params.type) || (await axios.get(`/api/statistic/${params.player}/${params.type}`));
        console.log(result);
        
        setStats(result.data);
      } catch (error) {
        console.error(error);
      }
    };

    handlerStats();
  }, [params.type]);

  if (!stats) return;

  const handleTranslate = (value) => {
    const keyLower = value.toLowerCase();

    const blocks = `block.minecraft.${keyLower}`;
    const items = `item.minecraft.${keyLower}`;
    const entities = `entity.minecraft.${keyLower}`;
    const stats = `stat.minecraft.${keyLower}`;

    return ru[blocks] || ru[items] || ru[entities] || ru[stats] || category[value] || value;
  };

  return (
    <section className={s.detailSection}>
      <Container>
        <h1 className={s.title}>{`Статистика игрока ${params.player}`}</h1>
        <h2 className={s.subtitle}>{`Категория: ${params.type}`}</h2>

        <ul className={s.grid}>
          {Object.entries(stats).map(([objName, objValue]) => {
            if (!objValue || Object.keys(objValue).length === 0) return null;

            return (
              <li key={objName} className={s.groupCard}>
                <h3 className={s.groupTitle}>{handleTranslate(objName)}</h3>

                <div className={s.statsList}>
                  {Object.entries(objValue).map(([key, value]) => (
                    <p key={key} className={s.statRow}>
                      {handleTranslate(key)}
                      <span>{new Intl.NumberFormat("ru-RU").format(value)}</span>
                    </p>
                  ))}
                </div>
              </li>
            );
          })}
        </ul>
      </Container>
    </section>
  );
};
