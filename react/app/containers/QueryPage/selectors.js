/**
 * QueryPage selectors
 */

import { createSelector } from 'reselect';

const selectHome = (state) => state.get('home');

const makeSelectCountryName = () => createSelector(
  selectHome,
  (homeState) => homeState.get('username')
);

export {
  selectHome,
  makeSelectCountryName,
};
