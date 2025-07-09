// src/pages/ProfilePage.js
import React from 'react';
import { useAuth } from '../context/AuthContext';
import { Link } from 'react-router-dom';
import './ProfilePage.css'; // Импортируем наши стили

const ProfilePage = () => {
    const { user, logout } = useAuth();

    if (!user) {
        return <div>Загрузка данных пользователя...</div>;
    }

    // Получаем первую букву имени для аватара
    const avatarLetter = user.full_name ? user.full_name.charAt(0).toUpperCase() : '?';

    return (
        <div className="profile-container">
            <header className="profile-header">
                <div className="profile-avatar">{avatarLetter}</div>
                <div className="profile-info">
                    <h2>{user.full_name}</h2>
                    <p>{user.email}</p>
                </div>
            </header>

            <nav className="profile-nav">
                <Link to="/orders" className="profile-nav-link">
                    Мои заказы
                </Link>
                <Link to="/favorites" className="profile-nav-link">
                    Избранные конфигурации
                </Link>
                <Link to="#" className="profile-nav-link">
                    Личные данные
                </Link>
            </nav>

            <button onClick={logout} className="logout-button">
                Выйти
            </button>
        </div>
    );
};

export default ProfilePage;