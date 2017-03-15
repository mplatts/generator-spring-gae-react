import React from 'react';
import { Button } from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';

/**
 * Page telling the user that their application was submitted successfully.
 */
const NotFoundPage = () => (
  <div>
    <h1>Not Found</h1>
    <p>
      Sorry, we can&#39;t find the page you&#39;re looking for.
    </p>
    <LinkContainer to="/">
      <Button bsStyle="primary">Home</Button>
    </LinkContainer>
  </div>
);

export default NotFoundPage;
