import React from "react";
import { NavLink } from "react-router-dom";

const nav = () => {
  const links = [
    { path: "/", label: "Home", exact: true },
    { path: "/builds", label: "Builds" }
  ];

  return (
    <nav>
      <ul>
        {links.map(({ path, label, exact }) => (
          <li key={path}>
            <NavLink to={path} exact={exact}>
              {label}
            </NavLink>
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default nav;
