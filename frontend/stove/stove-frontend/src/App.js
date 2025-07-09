// src/App.js

import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import { AuthProvider } from './context/AuthContext';

import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';

import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import ConstructorPage from './pages/ConstructorPage';
import ProfilePage from './pages/ProfilePage';
import OrdersPage from './pages/OrdersPage';
import FavoritesPage from './pages/FavoritesPage';
import NotFoundPage from './pages/NotFoundPage';
import BuildStovePage from './pages/BuildStovePage';
import CheckoutPage from './pages/CheckoutPage';         // <-- Импорт новой страницы
import OrderSuccessPage from './pages/OrderSuccessPage'; // <-- Импорт новой страницы

// Стили
import './styles/global.css';

function App() {
    return (
        <Router>
            <AuthProvider>
                <Routes>
                    {/* Роуты с общим Layout (нижняя навигация) */}
                    <Route path="/" element={<Layout />}>
                        <Route index element={<HomePage />} />
                        <Route path="constructor" element={<ConstructorPage />} />
                        <Route path="constructor/build/:stoveTypeId" element={<BuildStovePage />} />

                        {/* Защищенные роуты, требующие авторизации */}
                        <Route element={<ProtectedRoute />}>
                            <Route path="profile" element={<ProfilePage />} />
                            <Route path="orders" element={<OrdersPage />} />
                            <Route path="favorites" element={<FavoritesPage />} />
                            <Route path="checkout/:configurationId" element={<CheckoutPage />} /> {/* <-- Новый роут */}
                            <Route path="order-success" element={<OrderSuccessPage />} />     {/* <-- Новый роут */}
                        </Route>
                    </Route>

                    {/* Роуты без общего Layout */}
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegisterPage />} />

                    {/* Страница не найдена */}
                    <Route path="*" element={<NotFoundPage />} />
                </Routes>
            </AuthProvider>
        </Router>
    );
}

export default App;