import React from 'react';
import ReactDOM from 'react-dom';
import { AppContainer } from 'react-hot-loader';
import injectTapEventPlugin from 'react-tap-event-plugin';
import loadPolyfills from './polyfills';
import App from './components/App';
import '../less/styles/main.less';

// Needed for onTouchTap
// http://stackoverflow.com/a/34015469/988941
injectTapEventPlugin();

// Define render as a function so we can re-render when using Hot Module Replacement
const render = (Component) => {
  ReactDOM.render(
    <AppContainer>
      <Component />
    </AppContainer>,
    document.getElementById('root'),
  );
};

loadPolyfills(() => {
  // Perform initial render
  render(App);

  // Wire in Hot Module Replacement
  if (module.hot) {
    module.hot.accept('./components/App', () => render(App));
  }
});
