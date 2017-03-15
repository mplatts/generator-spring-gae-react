import React, { PropTypes } from 'react';
import { Navbar, Nav, Grid, Row, Col } from 'react-bootstrap';
import { Link } from 'react-router';

const Layout = props => (
  <div id="mainLayout">
    <Navbar inverse fixedTop>
      <Navbar.Header>
        <Navbar.Brand>
          <Link to="/">My App</Link>
        </Navbar.Brand>
      </Navbar.Header>
      <Nav/>
    </Navbar>
    <Grid>
      <Row>
        <Col xs={12}>
          { props.children }
        </Col>
      </Row>
    </Grid>
  </div>
);

Layout.propTypes = {
  children: PropTypes.element.isRequired,
};

export default Layout;
