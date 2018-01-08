import {
  CHANGE_COUNTRYNAME,
} from './constants';

/**
 * Changes the input field of the form
 *
 * @param  {name} name The new text of the input field
 *
 * @return {object}    An action object with a type of CHANGE_USERNAME
 */
export function changeCountryName(name) {
  return {
    type: CHANGE_COUNTRYNAME,
    name,
  };
}
