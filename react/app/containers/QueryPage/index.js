/*
 * QueryPage
 *
 * This is the first thing users see of our App, at the '/' route
 */

import React from 'react';
import PropTypes from 'prop-types';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
import { connect } from 'react-redux';
import { compose } from 'redux';
import { createStructuredSelector } from 'reselect';

import injectReducer from 'utils/injectReducer';
import injectSaga from 'utils/injectSaga';
import { makeSelectRepos, makeSelectLoading, makeSelectError } from 'containers/App/selectors';
import H2 from 'components/H2';
import H3 from 'components/H3';
import AtPrefix from './AtPrefix';
import CenteredSection from './CenteredSection';
import Form from './Form';
import Input from './Input';
import Section from './Section';
import messages from './messages';
import { loadRepos } from '../App/actions';
import { changeCountryName } from './actions';
import { makeSelectCountryName } from './selectors';
import reducer from './reducer';
import saga from './saga';
import TextField from 'material-ui/TextField';
export class QueryPage extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function
  /**
   * when initial state username is not null, submit the form to load repos
   */
  componentDidMount() {
    if (this.props.username && this.props.username.trim().length > 0) {
      this.props.onSubmitForm();
    }
  }

  render() {
    const { loading, error, repos } = this.props;
    const reposListProps = {
      loading,
      error,
      repos,
    };

    return (

      <article>
        <div>
          <Section>
            <H3>
              <FormattedMessage {...messages.typeValue} />
            </H3>
            <Form onSubmit={this.props.onSubmitForm}>
              <label htmlFor="countryName">
                <TextField
                  id="country name"
                  type="text"
                  placeholder="Name"
                  value={this.props.username}
                  onChange={this.props.onChangeUsername}
                />
                OR
                <TextField
                  id="country name"
                  type="text"
                  placeholder="Code"
                  value={this.props.username}
                  onChange={this.props.onChangeUsername}
                />
              </label>
            </Form>
          </Section>
        </div>
      </article>
    );
  }
}

QueryPage.propTypes = {
  loading: PropTypes.bool,
  error: PropTypes.oneOfType([
    PropTypes.object,
    PropTypes.bool,
  ]),
  countires: PropTypes.oneOfType([
    PropTypes.array,
    PropTypes.bool,
  ]),
  onSubmitForm: PropTypes.func,
  countryName: PropTypes.string,
  onChangeCountryName: PropTypes.func,
};

export function mapDispatchToProps(dispatch) {
  return {
    onChangeCountryName: (evt) => dispatch(changeUsername(evt.target.value)),
    onSubmitForm: (evt) => {
      if (evt !== undefined && evt.preventDefault) evt.preventDefault();
      dispatch(loadRepos());
    },
  };
}

const mapStateToProps = createStructuredSelector({
  repos: makeSelectRepos(),
  username: makeSelectCountryName(),
  loading: makeSelectLoading(),
  error: makeSelectError(),
});

const withConnect = connect(mapStateToProps, mapDispatchToProps);

const withReducer = injectReducer({ key: 'home', reducer });
const withSaga = injectSaga({ key: 'home', saga });

export default compose(
  withReducer,
  withSaga,
  withConnect,
)(QueryPage);
