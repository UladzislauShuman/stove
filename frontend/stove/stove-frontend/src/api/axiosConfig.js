// src/api/axiosConfig.js
import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080', // Адрес вашего бэкенда
});

// Этот перехватчик будет добавлять токен авторизации ко всем запросам,
// если он есть в локальном хранилище.
apiClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('authToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default apiClient;