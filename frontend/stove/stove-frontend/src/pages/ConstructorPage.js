// src/pages/ConstructorPage.js

import React, { useState, useEffect } from 'react';
import apiClient from '../api/axiosConfig';
import StoveTypeCard from '../components/StoveTypeCard'; // Импортируем нашу карточку
import './ConstructorPage.css'; // Импортируем стили для контейнера

const ConstructorPage = () => {
    const [stoveTypes, setStoveTypes] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchStoveTypes = async () => {
            try {
                const response = await apiClient.get('/constructor-data/stove-types');
                setStoveTypes(response.data);
            } catch (err) {
                setError('Не удалось загрузить данные для конструктора.');
                console.error(err);
            } finally {
                setIsLoading(false);
            }
        };

        fetchStoveTypes();
    }, []); // Пустой массив зависимостей означает, что эффект выполнится 1 раз при монтировании

    if (isLoading) {
        return <div>Загрузка конструктора...</div>;
    }

    if (error) {
        return <div style={{ color: 'red' }}>{error}</div>;
    }

    return (
        <div className="constructor-container">
            <h1>Конструктор</h1>
            <h2>Шаг 1: Выберите тип сооружения</h2>
            <div className="cards-grid">
                {stoveTypes.map((type) => (
                    // Передаем весь объект 'type' в компонент карточки
                    <StoveTypeCard
                        key={type.id}
                        type={type}
                    />
                ))}
            </div>
        </div>
    );
};

export default ConstructorPage;