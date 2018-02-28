import React, { Component } from "react";
import * as PropTypes from 'prop-types';
import { logComponentError } from "../util/errors";

class ErrorBoundary extends Component {

  static propTypes = {
    children: PropTypes.node.isRequired,
    renderError: PropTypes.oneOf([PropTypes.func, PropTypes.object])
  };

  static defaultProps = {
    renderError: undefined,
  };

  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidCatch(error, { componentStack }) {
    logComponentError(error, componentStack);
    console.error(componentStack, error);

    this.setState({ hasError: true, error, componentStack });
  }

  componentDidUpdate(prevProps) {
    if (prevProps.children !== this.props.children) {
      this.setState({
        hasError: false,
      });
    }
  }

  render() {
    const { hasError, error, info } = this.state;
    const { children, renderError: Error } = this.props;

    if (hasError) {
      if (Error) {
        return <Error error={error} info={info} />;
      }
      return <div>
        An error has occured and been logged.
      </div>;
    }

    return children;
  }
}

export default ErrorBoundary;
