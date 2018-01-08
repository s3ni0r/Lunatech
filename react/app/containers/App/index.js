
import React from 'react';
import AppBar from 'material-ui/AppBar';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import styled from 'styled-components';
import {Switch, Route} from 'react-router-dom';
import {Tabs, Tab} from 'material-ui/Tabs';
import FontIcon from 'material-ui/FontIcon';
import ActioReport from 'material-ui/svg-icons/action/dashboard';
import ActioQuery from 'material-ui/svg-icons/action/search';

import ReportPage from 'containers/ReportPage/Loadable';
import QueryPage from 'containers/QueryPage/Loadable';
import NotFoundPage from 'containers/NotFoundPage/Loadable';


const AppWrapper = styled.div`
  max-width: calc(768px + 16px * 2);
  margin: 0 auto;
  display: flex;
  min-height: 100%;
  padding: 0 16px;
  flex-direction: column;
`;

function handleActive(tab) {
  alert(`A tab with this route property ${tab.props['data-route']} was activated.`);
}
const AppTabs = () => (
  <Tabs>
    <Tab icon={<ActioQuery/>} data-route="/query" onActive={handleActive} />
    <Tab icon={<ActioReport/>} data-route="/report" onActive={handleActive} />
  </Tabs>
);

export default function App() {
  return (
    <MuiThemeProvider>
      <div>
        <AppTabs/>
        <AppWrapper>
          <Switch>
            <Route exact path="/" component={QueryPage}/>
            <Route  path="/query" component={QueryPage}/>
            <Route  path="/report" component={NotFoundPage}/>
          </Switch>
        </AppWrapper>
      </div>
    </MuiThemeProvider>
  );
}
