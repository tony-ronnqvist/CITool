import React from "react";
import ScaleLoader from "react-spinners/ScaleLoader";

const spinner = ({ mt }) => (
  <div style={{ marginTop: `${mt}rem` }}>
    <ScaleLoader color="#F1C40F" />
  </div>
);

export default spinner;
