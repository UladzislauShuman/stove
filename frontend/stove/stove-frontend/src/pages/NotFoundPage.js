import React from 'react';
import { Link } from 'react-router-dom';

const NotFoundPage = () => {
    return (
        <div style={{ textAlign: 'center', marginTop: '100px' }}>
            <h1>404</h1>
            <h2>Страница не найдена</h2>
            <p>Кажется, вы заблудились. Давайте вернемся на главную.</p>
            <Link to="/">
                <button>На главную</button>
            </Link>
        </div>
    );
};

export default NotFoundPage;