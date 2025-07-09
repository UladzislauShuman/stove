// src/components/Layout.js
import React from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';
import './Layout.css'; // Создадим этот файл для стилей чуть позже

const Layout = () => {
    const location = useLocation();

    const getLinkClass = (path) => {
        return location.pathname === path ? 'nav-link active' : 'nav-link';
    };

    return (
        <div className="app-container">
            <main className="main-content">
                <Outlet /> {/* Здесь будет отображаться контент текущей страницы */}
            </main>
            <footer className="bottom-nav">
                <Link to="/" className={getLinkClass('/')}>
                    {/* Тут можно вставить иконки позже */}
                    <span>Главная</span>
                </Link>
                <Link to="/constructor" className={getLinkClass('/constructor')}>
                    <span>Конструктор</span>
                </Link>
                <Link to="/profile" className={getLinkClass('/profile')}>
                    <span>Профиль</span>
                </Link>
            </footer>
        </div>
    );
};

export default Layout;