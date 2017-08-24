import React from 'react';
import { Router, Route, IndexRoute } from 'react-router';
import { history } from '../store';
import {
  composeOnEnterHooks,
  loginRequired,
  hasAnyRole,
} from './hooks';
import Layout from '../pages/Layout';
import AdminLayout from '../pages/admin/AdminLayout';
import DashboardPage from '../pages/admin/DashboardPage';
import HomePage from '../pages/HomePage';
import NotFoundPage from '../pages/NotFoundPage';
import LoginPage from '../pages/login/LoginPage';

/**
 * Define frontend routes.
 */
const getRoutes = () => (
  <Router history={history}>
    <Route path="/" component={Layout}>
      <IndexRoute component={HomePage} />
      <Route path="/login" component={LoginPage} />

      <Route
        path="/admin"
        component={AdminLayout}
        onEnter={composeOnEnterHooks(loginRequired, hasAnyRole('admin'))}
      >
        <IndexRoute component={DashboardPage} />
      </Route>

      <Route path="*" component={NotFoundPage} />
    </Route>
  </Router>
);

export default getRoutes;
