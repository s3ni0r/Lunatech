/*
 * AppConstants
 * Each action has a corresponding type, which the reducer knows and picks up on.
 * To avoid weird typos between the reducer and the actions, we save them as
 * constants here. We prefix them with 'yourproject/YourComponent' so we avoid
 * reducers accidentally picking up actions they shouldn't.
 *
 * Follow this format:
 * export const YOUR_ACTION_CONSTANT = 'yourproject/YourContainer/YOUR_ACTION_CONSTANT';
 */

export const LOAD_COUNTRIES_BY_NAME = 'LOAD_COUNTRIES_BY_NAME';
export const LOAD_COUNTRIES_BY_CODE = 'LOAD_COUNTRIES_BY_CODE';
export const LOAD_TOPFLOP_COUNTRIES = 'LOAD_TOPFLOP_COUNTRIES';
export const LOAD_SURFACES_BY_COUNTY = 'LOAD_SURFACES_BY_COUNTY';
export const DEFAULT_LOCALE = 'en';
