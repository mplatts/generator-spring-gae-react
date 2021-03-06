import { isFunction } from 'lodash';
import { SubmissionError } from 'redux-form';

export const toSubmissionError = error => (error.messages ? new SubmissionError({ _error: error }) : error);

export const asyncAction =
  (prefix, promiseOrPromiseCreator, options = {}) => (dispatch, getState) => {
    const {
      responseTransformer = res => res,
      onSuccess = res => res,
      onFailure = (error) => {
        throw error;
      },
      transformError = error => error,
    } = options;

    console.log(`${prefix}_INPROGRESS`);
    dispatch({
      type: `${prefix}_INPROGRESS`,
    });

    let promise;
    if (isFunction(promiseOrPromiseCreator)) {
      promise = promiseOrPromiseCreator(getState);
    } else {
      promise = promiseOrPromiseCreator;
    }

    return promise
      .then((res) => {
        console.log(`${prefix}_SUCCESS`);
        dispatch({
          type: `${prefix}_SUCCESS`,
          response: responseTransformer(res),
        });
        return onSuccess(res, dispatch, getState);
      }, (error) => {
        console.error('Async request error:', error);
        dispatch({
          type: `${prefix}_FAILED`,
          errorMessage: error.message,
        });
        return onFailure(error, dispatch, getState);
      })
      .catch((error) => {
        console.error('Unhandled error', error);
        throw transformError(error);
      });
  };

export default {
  asyncAction,
};
