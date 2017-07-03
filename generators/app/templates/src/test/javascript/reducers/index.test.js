import rootReducer from '../../../../src/main/static/javascript/reducers';

describe('Root reducer', () => {

  test('returns form in state', () => {
    const state = rootReducer();

    expect(state.form).toBeDefined();
  })

  test('returns routing in state', () => {
    const state = rootReducer();

    expect(state.routing).toBeDefined();
  })
});
