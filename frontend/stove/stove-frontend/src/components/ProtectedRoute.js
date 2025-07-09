// src/components/ProtectedRoute.js
import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const ProtectedRoute = () => {
    const { user } = useAuth();

    if (!user) {
        // Если пользователя нет, перенаправляем на страницу входа
        return <Navigate to="/login" replace />;
    }

    // Если есть, рендерим вложенный роут
    return <Outlet />;
};

export default ProtectedRoute;