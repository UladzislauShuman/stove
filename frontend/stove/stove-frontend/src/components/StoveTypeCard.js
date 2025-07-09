// src/components/StoveTypeCard.js
import React from 'react';
import { Link } from 'react-router-dom';
import './StoveTypeCard.css';

// Принимаем весь объект type целиком
const StoveTypeCard = ({ type }) => {
    return (
        // Добавляем атрибут `state` в Link. Сюда можно передать любые данные.
        <Link to={`/constructor/build/${type.id}`} state={{ stoveTypeData: type }} className="stove-card-link">
            <div className="stove-card">
                <img src={type.image_url} alt={type.name} className="stove-card-image" />
                <div className="stove-card-content">
                    <h3 className="stove-card-title">{type.name}</h3>
                    <p className="stove-card-description">{type.description}</p>
                    <div className="stove-card-footer">
                        <span className="stove-card-price">от {type.base_price} ₽</span>
                        <span className="stove-card-select">Выбрать →</span>
                    </div>
                </div>
            </div>
        </Link>
    );
};

export default StoveTypeCard;