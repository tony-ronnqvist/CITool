import React from "react";
import PropTypes from "prop-types";

import Spinner from "./spinner";

const withLoader = ({ loading, children }) =>
  loading ? <Spinner /> : children;

withLoader.propTypes = {
  loading: PropTypes.bool.isRequired
};

export default withLoader;
