import React, { useState, useEffect } from 'react';
import apiClient from '../api/axiosConfig';
import { Link } from 'react-router-dom';

const OrdersPage = () => {
    const [orders, setOrders] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await apiClient.get('/api/v1/orders');
                setOrders(response.data);
            } catch (err) {
                setError('Не удалось загрузить ваши заказы.');
                console.error(err);
            } finally {
                setIsLoading(false);
            }
        };
        fetchOrders();
    }, []);

    if (isLoading) return <div>Загрузка заказов...</div>;
    if (error) return <div style={{ color: 'red' }}>{error}</div>;

    return (
        <div>
            <h2>Мои заказы</h2>
            {orders.length === 0 ? (
                <p>У вас пока нет заказов. <Link to="/constructor">Создайте свой первый!</Link></p>
            ) : (
                orders.map(order => (
                    <div key={order.id} style={{ border: '1px solid #ccc', padding: '10px', marginBottom: '10px' }}>
                        <h3>Заказ №{order.id} - {order.configuration.name}</h3>
                        <p>Статус: {order.status}</p>
                        <p>Дата создания: {new Date(order.created_at).toLocaleDateString()}</p>
                        <p>Итоговая цена: {order.final_price > 0 ? `${order.final_price} у.е.` : 'Уточняется'}</p>
                    </div>
                ))
            )}
        </div>
    );
};

export default OrdersPage;