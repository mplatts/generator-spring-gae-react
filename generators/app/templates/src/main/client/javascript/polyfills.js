/**
 * We take an all or nothing approach to polyfills. Partial support will result in all polyfills
 * being loaded regardless.
 *
 * @see https://philipwalton.com/articles/loading-polyfills-only-when-needed/
 */
const browserSupportsAllFeatures = () =>
  window.Promise &&
  window.fetch &&
  window.Intl;

/**
 * Conditionally load polyfills based on browser support.
 *
 * @param done callback to execute when loading is complete
 */
const loadPolyfills = (done) => {
  if (browserSupportsAllFeatures()) {
    done();  // Polyfills aren't required
  } else {
    // Webpack parses the inside of require.ensure at build time to know that polyfills
    // should be bundled separately from the main chunk.
    require.ensure([], (require) => {
      require('babel-polyfill');
      require('whatwg-fetch');
      require('intl');

      done();  // Resume loading the app
    });
  }
};

export default loadPolyfills;
