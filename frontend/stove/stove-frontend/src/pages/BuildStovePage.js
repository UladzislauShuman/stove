// src/pages/BuildStovePage.js

import React, { useState, useEffect, useMemo } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { useAuth } from '../context/AuthContext';

const BuildStovePage = () => {
    const { stoveTypeId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const { user } = useAuth();

    const [stoveType, setStoveType] = useState(location.state?.stoveTypeData || null);

    const [components, setComponents] = useState([]);
    const [addons, setAddons] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [selection, setSelection] = useState({});
    const [selectedAddons, setSelectedAddons] = useState(new Set());
    const [configName, setConfigName] = useState("Моя новая печь");

    useEffect(() => {
        if (!stoveTypeId) {
            setError("Не указан ID типа печи.");
            setLoading(false);
            return;
        }

        const fetchData = async () => {
            try {
                setLoading(true);
                setError(null);

                const [componentsRes, addonsRes] = await Promise.all([
                    apiClient.get(`/constructor-data/stove-types/${stoveTypeId}/components`),
                    apiClient.get('/constructor-data/addons')
                ]);

                setAddons(addonsRes.data);
                const componentsData = componentsRes.data;

                const componentsWithOptions = await Promise.all(
                    componentsData.map(async (component) => {
                        const optionsRes = await apiClient.get(`/constructor-data/components/${component.id}/options`);
                        return { ...component, options: Array.isArray(optionsRes.data) ? optionsRes.data : [] };
                    })
                );

                setComponents(componentsWithOptions);

                if (!stoveType) {
                    const allTypesRes = await apiClient.get('/constructor-data/stove-types');
                    const currentStoveType = allTypesRes.data.find(t => t.id === parseInt(stoveTypeId));
                    if (currentStoveType) {
                        setStoveType(currentStoveType);
                    } else {
                        throw new Error(`Тип печи с ID ${stoveTypeId} не найден`);
                    }
                }

            } catch (err) {
                console.error("Ошибка при загрузке данных конструктора:", err);
                setError("Не удалось загрузить конфигурацию. Проверьте консоль для деталей.");
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [stoveTypeId]);

    const totalPrice = useMemo(() => {
        if (!stoveType || components.length === 0) return 0;

        let total = stoveType.base_price;

        for (const componentId in selection) {
            const optionId = selection[componentId];
            const component = components.find(c => c.id === parseInt(componentId));
            const option = component?.options.find(o => o.id === optionId);
            if (option) {
                total += option.price_modifier;
            }
        }

        selectedAddons.forEach(addonId => {
            const addon = addons.find(a => a.id === addonId);
            if (addon) {
                total += addon.price;
            }
        });

        return total;
    }, [selection, selectedAddons, stoveType, components, addons]);


    const handleSelectOption = (componentId, optionId) => {
        setSelection(prev => ({ ...prev, [componentId]: optionId }));
    };

    const handleToggleAddon = (addonId) => {
        setSelectedAddons(prev => {
            const newSet = new Set(prev);
            if (newSet.has(addonId)) {
                newSet.delete(addonId);
            } else {
                newSet.add(addonId);
            }
            return newSet;
        });
    };

    const handleSaveConfiguration = async () => {
        if (!user) {
            alert("Пожалуйста, войдите в систему, чтобы сохранить конфигурацию.");
            navigate('/login', { state: { from: location } });
            return;
        }

        const payload = {
            name: configName,
            stove_type_id: parseInt(stoveTypeId),
            choices: Object.values(selection).map(optionId => ({ option_id: optionId })),
            addons: Array.from(selectedAddons).map(addonId => ({ addon_id: addonId }))
        };

        try {
            const response = await apiClient.post('/api/v1/configurations', payload);
            alert(`Конфигурация "${response.data.name}" успешно сохранена!`);
            navigate('/favorites');
        } catch (err) {
            console.error("Ошибка сохранения конфигурации:", err);
            alert("Не удалось сохранить конфигурацию.");
        }
    };

    // --- НОВАЯ ФУНКЦИЯ ДЛЯ ПЕРЕХОДА К ОФОРМЛЕНИЮ ЗАКАЗА ---
    const handleProceedToCheckout = async () => {
        if (!user) {
            alert("Пожалуйста, войдите в систему, чтобы оформить заказ.");
            navigate('/login', { state: { from: location } });
            return;
        }

        const payload = {
            name: configName,
            stove_type_id: parseInt(stoveTypeId),
            choices: Object.values(selection).map(optionId => ({ option_id: optionId })),
            addons: Array.from(selectedAddons).map(addonId => ({ addon_id: addonId }))
        };

        try {
            const response = await apiClient.post('/api/v1/configurations', payload);
            const newConfiguration = response.data;

            // Переходим на страницу оформления, передавая ей данные
            navigate(`/checkout/${newConfiguration.id}`, {
                state: {
                    configuration: {
                        id: newConfiguration.id,
                        name: newConfiguration.name,
                        stove_type: stoveType, // Передаем уже загруженный объект
                        total_price: totalPrice // Передаем рассчитанную цену
                    }
                }
            });

        } catch (err) {
            console.error("Ошибка при подготовке к оформлению заказа:", err);
            alert("Не удалось сохранить конфигурацию перед оформлением заказа.");
        }
    };


    if (loading) return <div>Загрузка конфигурации...</div>;
    if (error) return <div>{error} <button onClick={() => navigate('/constructor')}>Назад</button></div>;

    return (
        <div style={{ padding: '20px', maxWidth: '900px', margin: 'auto' }}>
            <button onClick={() => navigate('/constructor')}>← Назад к выбору типа</button>
            <h1>Конструктор для: {stoveType?.name}</h1>

            {components.map(component => (
                <div key={component.id} style={{ border: '1px solid #eee', padding: '15px', margin: '20px 0', borderRadius: '12px' }}>
                    <h2>{component.name}</h2>
                    <p>{component.description}</p>
                    <div style={{ display: 'flex', gap: '10px', flexWrap: 'wrap' }}>
                        {component.options.map(option => (
                            <div
                                key={option.id}
                                onClick={() => handleSelectOption(component.id, option.id)}
                                style={{
                                    border: selection[component.id] === option.id ? '2px solid var(--primary-color)' : '1px solid #ccc',
                                    padding: '10px',
                                    cursor: 'pointer',
                                    textAlign: 'center',
                                    borderRadius: '8px',
                                    transition: 'border-color 0.2s'
                                }}
                            >
                                <img src={option.image_url} alt={option.name} style={{ width: '150px', height: '100px', objectFit: 'cover', borderRadius: '4px' }} />
                                <p style={{ margin: '8px 0 0 0' }}>{option.name}</p>
                                <b style={{ color: '#374151' }}>+{option.price_modifier} ₽</b>
                            </div>
                        ))}
                    </div>
                </div>
            ))}

            <div style={{ border: '1px solid #eee', padding: '15px', margin: '20px 0', borderRadius: '12px' }}>
                <h2>Дополнительные услуги</h2>
                {addons.map(addon => (
                    <div key={addon.id} style={{ padding: '5px 0' }}>
                        <label style={{ cursor: 'pointer', display: 'flex', alignItems: 'center' }}>
                            <input
                                type="checkbox"
                                checked={selectedAddons.has(addon.id)}
                                onChange={() => handleToggleAddon(addon.id)}
                                style={{ marginRight: '10px', width: '18px', height: '18px' }}
                            />
                            <span><strong>{addon.name} (+{addon.price} ₽)</strong> - {addon.description}</span>
                        </label>
                    </div>
                ))}
            </div>

            <div style={{ marginTop: '30px', padding: '20px', backgroundColor: '#f3f4f6', borderRadius: '12px' }}>
                <h2>Итог</h2>
                <div style={{ marginBottom: '15px' }}>
                    <label htmlFor="configName" style={{ marginRight: '10px' }}>Название вашей конфигурации:</label>
                    <input
                        id="configName"
                        type="text"
                        value={configName}
                        onChange={(e) => setConfigName(e.target.value)}
                        style={{ padding: '8px', borderRadius: '4px', border: '1px solid #ccc' }}
                    />
                </div>
                <h3>Итоговая стоимость: <span style={{ color: 'var(--primary-color)' }}>{totalPrice} ₽</span></h3>

                <div style={{ marginTop: '20px', display: 'flex', gap: '15px' }}>
                    <button onClick={handleSaveConfiguration} style={{ padding: '10px 20px', cursor: 'pointer' }}>Сохранить в избранное</button>
                    <button
                        onClick={handleProceedToCheckout} // <-- Привязываем новую функцию
                        style={{ padding: '10px 20px', cursor: 'pointer', backgroundColor: 'var(--primary-color)', color: 'white', border: 'none' }}
                    >
                        Оформить заказ
                    </button>
                </div>
                <p style={{fontSize: '0.8em', color: '#6b7280'}}>* Для сохранения или заказа необходимо войти в систему.</p>
            </div>
        </div>
    );
};

export default BuildStovePage;