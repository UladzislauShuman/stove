// src/pages/CheckoutPage.js

import React, { useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { useAuth } from '../context/AuthContext';

const CheckoutPage = () => {
    const { configurationId } = useParams();
    const navigate = useNavigate();
    const location = useLocation(); // Чтобы получить данные о конфигурации
    const { user } = useAuth();

    // Получаем данные о конфигурации, переданные со страницы конструктора
    const { configuration } = location.state || {};

    // Состояния для полей формы
    const [customerName, setCustomerName] = useState(user?.full_name || '');
    const [customerPhone, setCustomerPhone] = useState(user?.phone_number || '');
    const [objectAddress, setObjectAddress] = useState('');
    const [customerComment, setCustomerComment] = useState('');

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleSubmitOrder = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        const payload = {
            configuration_id: parseInt(configurationId),
            customer_name: customerName,
            customer_phone: customerPhone,
            object_address: objectAddress,
            customer_comment: customerComment
        };

        try {
            await apiClient.post('/api/v1/orders', payload);
            // При успехе перенаправляем на страницу благодарности
            navigate('/order-success');
        } catch (err) {
            console.error("Ошибка при оформлении заказа:", err);
            setError('Не удалось оформить заказ. Попробуйте снова.');
            setLoading(false);
        }
    };

    if (!configuration) {
        return <div>Ошибка: данные о конфигурации не найдены. <button onClick={() => navigate('/')}>На главную</button></div>;
    }

    return (
        <div style={{ maxWidth: '600px', margin: 'auto', padding: '20px' }}>
            <h2>Оформление заказа</h2>

            <div style={{ background: '#f9f9f9', padding: '15px', borderRadius: '8px', marginBottom: '20px' }}>
                <h4>Ваша конфигурация:</h4>
                <p><strong>Название:</strong> {configuration.name}</p>
                <p><strong>Тип:</strong> {configuration.stove_type.name}</p>
                <p><strong>Итоговая стоимость:</strong> {configuration.total_price} ₽</p>
            </div>

            <form onSubmit={handleSubmitOrder}>
                <div style={{ marginBottom: '15px' }}>
                    <label>Ваше имя</label>
                    <input type="text" value={customerName} onChange={e => setCustomerName(e.target.value)} required style={{ width: '100%', padding: '8px' }}/>
                </div>
                <div style={{ marginBottom: '15px' }}>
                    <label>Контактный телефон</label>
                    <input type="tel" value={customerPhone} onChange={e => setCustomerPhone(e.target.value)} required style={{ width: '100%', padding: '8px' }}/>
                </div>
                <div style={{ marginBottom: '15px' }}>
                    <label>Адрес объекта (город, улица, дом)</label>
                    <input type="text" value={objectAddress} onChange={e => setObjectAddress(e.target.value)} required style={{ width: '100%', padding: '8px' }}/>
                </div>
                <div style={{ marginBottom: '15px' }}>
                    <label>Комментарий к заказу</label>
                    <textarea value={customerComment} onChange={e => setCustomerComment(e.target.value)} style={{ width: '100%', padding: '8px', minHeight: '80px' }}></textarea>
                </div>

                {error && <p style={{ color: 'red' }}>{error}</p>}

                <button type="submit" disabled={loading} style={{ width: '100%', padding: '12px', fontSize: '1.2em' }}>
                    {loading ? 'Оформляем...' : 'Подтвердить заказ'}
                </button>
            </form>
        </div>
    );
};

export default CheckoutPage;