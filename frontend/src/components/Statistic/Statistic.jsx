import { Outlet, useNavigate, useParams } from "react-router-dom";
import { Container } from "../Container/Container";
import { useEffect, useState } from "react";
import { Globe, Heart, Swords, Hand, ArrowLeft, icons } from "lucide-react";
import axios from "axios";
import s from "./Statistic.module.css";
import { Stats } from "../assets/stats";

export const Statistic = () => {
  const [statistic, setStatistic] = useState(null);
  const navigate = useNavigate();
  let param = useParams();

  useEffect(() => {
    const handleStatistic = async () => {
      try {
        const result = Stats(param.player) || (await axios.get(`/api/statistic/${param.player}`));
        setStatistic(result.data);
      } catch (error) {
        navigate("notFound");
      }
    };
    handleStatistic();
  }, [param.player]);

  if (!statistic) return <div>Загрузка...</div>;

  const category = [
    {
      id: "world",
      icon: Globe,
      properties: [
        {
          name: "Выкинуто предметов",
          value: statistic.world?.DROP.TOTAL_DROP_ITEMS || 0,
        },
        {
          name: "Поднято предметов",
          value: statistic.world?.PICKUP.TOTAL_PICKUP_ITEMS || 0,
        },
      ],
    },
    {
      id: "survival",
      icon: Heart,
      properties: [
        {
          name: "Пройденная дистанция",
          value: statistic.survival?.properties.TOTAL_DISTANCE_TRAVELED || 0,
        },
        {
          name: "Всего съедено еды",
          value: statistic.survival?.EATEN.TOTAL_EATEN || 0,
        },
        {
          name: "Всего сломано экипировки",
          value: statistic.survival?.BREAK_ITEM.TOTAL_BREAK_ITEMS || 0,
        },
      ],
    },
    {
      id: "interact",
      icon: Hand,
      properties: [
        {
          name: "Всего создано предметов",
          value: statistic.interact?.CRAFT_ITEM.TOTAL_CRAFT_ITEMS || 0,
        },
        {
          name: "Всего сломано блоков",
          value: statistic.interact?.MINE_BLOCK.TOTAL_BREAK_BLOCKS || 0,
        },
        {
          name: "Всего поставленно блоков",
          value: statistic.interact?.PLACED_BLOCKS.TOTAL_PLACED_BLOCKS || 0,
        },
      ],
    },
    {
      id: "combat",
      icon: Swords,
      properties: [
        {
          name: "Всего смертей",
          value: statistic.combat?.properties.DEATHS || 0,
        },
        {
          name: "Всего убийств",
          value: statistic.combat?.KILL_ENTITY.TOTAL_KILLS || 0,
        },
        {
          name: "Всего убит монстрами",
          value: statistic.combat?.ENTITY_KILLED_BY.TOTAL_KILLED || 0,
        },
      ],
    },
  ];

  return (
    <section className={s.section}>
      <Container>
        <div className={s.playerInfo}>
          <img src={`https://mc-heads.net/avatar/${param.player}/64`} alt={`${param.player}`} />
          <h1>{`${param.player}`}</h1>
          <p>Статистика игрока</p>
        </div>

        <ul className={s.categoryList}>
          {category.map(({ id, icon, properties }) => {
            const Icon = icon;

            return (
              <li key={id} className={s.categoryItem} onClick={() => navigate(id)}>
                <div className={s.iconBox}>
                  <Icon size={24} />
                </div>

                <h1>{id.toUpperCase()}</h1>

                <div className={s.propList}>
                  {properties.map(({ name, value }) => (
                    <p key={name}>
                      {name} <span>{value}</span>
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
