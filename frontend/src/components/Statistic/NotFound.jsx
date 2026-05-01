import { useParams } from "react-router-dom";
import { Container } from "../Container/Container";
import { Link } from "react-router-dom";
import s from "./NotFound.module.css";

export const NotFound = () => {
  let params = useParams();

  return (
    <section className={s.errorSection}>
      <Container className={s.container}>
        <h1 className={s.errorCode}>404</h1>
        <h2 className={s.title}>Упс!</h2>
        <p className={s.message}>
          Игрок <span className={s.highlight}>{params.player}</span> не найден в базе данных.
        </p>

        <Link to="/" className={s.backLink}>
          Вернуться к поиску
        </Link>
      </Container>
    </section>
  );
};
