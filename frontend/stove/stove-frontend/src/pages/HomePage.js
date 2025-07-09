import React from 'react';
import { Link } from 'react-router-dom';

const HomePage = () => {
    return (
        <div>
            <h1>Мастерская печей и каминов</h1>
            <p>Создайте печь своей мечты с помощью нашего удобного конструктора.</p>

            {/* Пример того, как можно разместить блоки из вашего ТЗ */}
            <div style={{ marginTop: '40px' }}>
                <h2>Специальные предложения</h2>
                <div style={{ border: '1px solid #ccc', padding: '10px', marginBottom: '10px' }}>
                    <h3>Помпейская печь на прицепе!</h3>
                    <p>Готовое мобильное решение для вашего бизнеса или дачи.</p>
                </div>
                <div style={{ border: '1px solid #ccc', padding: '10px', marginBottom: '10px' }}>
                    <h3>При заказе Барбекю - Скидка на шампуры от Сережи</h3>
                    <p>Наши партнеры делают лучшие аксессуары для гриля.</p>
                </div>
            </div>

            <div style={{ marginTop: '20px' }}>
                <Link to="/constructor">
                    <button style={{ padding: '10px 20px', fontSize: '1.2em' }}>
                        Перейти в конструктор
                    </button>
                </Link>
            </div>
        </div>
    );
};

export default HomePage;