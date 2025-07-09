// src/context/AuthContext.js
import React, { createContext, useState, useEffect, useContext } from 'react';
import apiClient from '../api/axiosConfig';
import { useNavigate } from 'react-router-dom';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        // При загрузке приложения проверяем, есть ли токен
        const token = localStorage.getItem('authToken');
        if (token) {
            // Если есть, запрашиваем данные о пользователе
            apiClient.get('/api/v1/users/me')
                .then(response => {
                    setUser(response.data);
                })
                .catch(() => {
                    // Если токен невалидный, чистим
                    localStorage.removeItem('authToken');
                    setUser(null);
                })
                .finally(() => setLoading(false));
        } else {
            setLoading(false);
        }
    }, []);

    const login = async (email, password) => {
        const response = await apiClient.post('/api/v1/auth/login', { email, password });
        const token = response.data.token; // Проверьте, что ключ именно `token` в ответе API
        localStorage.setItem('authToken', token);
        // После установки токена, apiClient будет его использовать автоматически
        const userResponse = await apiClient.get('/api/v1/users/me');
        setUser(userResponse.data);
        navigate('/profile'); // Перенаправляем в профиль после логина
    };

    const register = async (fullName, phoneNumber, email, password) => {
        const response = await apiClient.post('/api/v1/auth/register', {
            full_name: fullName,
            phone_number: phoneNumber,
            email: email,
            password: password
        });
        const token = response.data.token; // И тут тоже
        localStorage.setItem('authToken', token);
        const userResponse = await apiClient.get('/api/v1/users/me');
        setUser(userResponse.data);
        navigate('/profile');
    };

    const logout = () => {
        localStorage.removeItem('authToken');
        setUser(null);
        navigate('/login');
    };

    const authContextValue = {
        user,
        loading,
        login,
        register,
        logout,
    };

    // Не рендерим приложение, пока не проверили токен
    return (
        <AuthContext.Provider value={authContextValue}>
            {!loading && children}
        </AuthContext.Provider>
    );
};

// Простой хук для удобного доступа к контексту
export const useAuth = () => {
    return useContext(AuthContext);
};