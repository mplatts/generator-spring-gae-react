import { Card, CardHeader, CardText } from 'material-ui';
import moment from 'moment';
import React from 'react';
import { compose } from 'redux';
import { Field, reduxForm } from 'redux-form';
import MaterialSelectField from '../components/common/MaterialSelectField';
import TimeAgo from '../components/common/TimeAgo';
import { defaultNull } from '../util/normalizers';
import './HomePage.less';

/**
 * Application home page.
 */

const options = [
  { name: 'Option 1', value: 'option1' },
  { name: 'Option 2', value: 'option2' },
  { name: 'Option 3', value: 'option3' },
];

const HomePage = () => (
  <div className="home-page">
    <h1 className="display-2">Hello 3wks!</h1>
    <h2 className="display-1">Some bundled widgets</h2>
    <div className="widgets">
      <div>
        Overwrite this page when you creath your app, but in the mean time here is some info ...
      </div>
      <Card>
        <CardHeader
          title="MaterialSelectField"
        />
        <CardText>
          <div>
            A select field that also supports typeahead and uses material look and feel.
          </div>
          <Field
            clearable={false}
            backspaceRemoves={false}
            deleteRemoves={false}
            name="MaterialSelectField"
            options={options}
            component={MaterialSelectField}
            label="Field label"
            normalize={defaultNull}
            valueKey="name"
            labelKey="name"
          />
        </CardText>
      </Card>
      <Card>
        <CardHeader
          title="TimeAgo"
        />
        <CardText>
          <p>
            A widget that displays a date/time as &quot;time ago&quot; and optionally
            click to expand to full date/time and collapse again.
          </p>
          <p>
            <TimeAgo value={moment().subtract(5, 'minutes').toDate()} expandable/>
          </p>
        </CardText>
      </Card>
    </div>
  </div>
);


const form = reduxForm({ form: 'widget-form' });
export default compose(form)(HomePage);
