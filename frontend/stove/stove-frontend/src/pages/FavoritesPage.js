import React, { useState, useEffect } from 'react';
import apiClient from '../api/axiosConfig';
import { Link } from 'react-router-dom';

const FavoritesPage = () => {
    const [configs, setConfigs] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Согласно вашему API, это, скорее всего, эндпоинт для получения всех конфигураций пользователя
        const fetchFavorites = async () => {
            try {
                const response = await apiClient.get('/api/v1/configurations');
                // Фильтруем, чтобы показать только те, что не заблокированы заказом
                setConfigs(response.data.filter(c => !c.is_locked));
            } catch (err) {
                setError('Не удалось загрузить избранные конфигурации.');
                console.error(err);
            } finally {
                setIsLoading(false);
            }
        };
        fetchFavorites();
    }, []);

    if (isLoading) return <div>Загрузка избранного...</div>;
    if (error) return <div style={{ color: 'red' }}>{error}</div>;

    return (
        <div>
            <h2>Избранные конфигурации (Черновики)</h2>
            {configs.length === 0 ? (
                <p>У вас нет сохраненных конфигураций. <Link to="/constructor">Создайте новую!</Link></p>
            ) : (
                configs.map(config => (
                    <div key={config.id} style={{ border: '1px solid #ccc', padding: '10px', marginBottom: '10px' }}>
                        <h3>{config.name}</h3>
                        <p>Тип: {config.stove_type.name}</p>
                        <p>Расчетная цена: {config.total_price} у.е.</p>
                        {/* Ссылка для продолжения редактирования */}
                        <Link to={`/constructor/edit/${config.id}`}>
                            <button>Продолжить</button>
                        </Link>
                    </div>
                ))
            )}
        </div>
    );
};

export default FavoritesPage;