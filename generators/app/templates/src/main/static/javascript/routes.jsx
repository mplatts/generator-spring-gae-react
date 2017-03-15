import React from 'react';
import { Router, Route, IndexRoute } from 'react-router';
import { history } from './store';
import { HomePage, NotFoundPage } from './components/pages';
import Layout from './components/Layout';

/**
 * Define frontend routes.
 */
const getRoutes = () => (
  <Router history={history}>
    <Route path="/" component={Layout}>
      <IndexRoute component={HomePage} />
      <Route path="*" component={NotFoundPage} />
    </Route>
  </Router>
);

export default getRoutes;
