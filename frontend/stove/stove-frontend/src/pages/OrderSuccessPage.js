// src/pages/OrderSuccessPage.js
import React from 'react';
import { Link } from 'react-router-dom';

const OrderSuccessPage = () => {
    return (
        <div style={{ textAlign: 'center', padding: '40px' }}>
            <h1>Спасибо за ваш заказ!</h1>
            <p>Ваш заказ успешно оформлен. Наш печник свяжется с вами в ближайшее время для уточнения деталей.</p>
            <div style={{ marginTop: '30px' }}>
                <Link to="/orders">
                    <button style={{ padding: '10px 20px', marginRight: '15px' }}>Посмотреть мои заказы</button>
                </Link>
                <Link to="/">
                    <button style={{ padding: '10px 20px' }}>Вернуться на главную</button>
                </Link>
            </div>
        </div>
    );
};

export default OrderSuccessPage;