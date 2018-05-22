import { shape, string, arrayOf, number } from 'prop-types';

export const error = shape({
  type: string,
  status: number,
  message: string,
  messages: arrayOf(string),
});
