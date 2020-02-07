import React from "react";
import { Link } from "react-router-dom";

import { useBuild } from "../util/hooks";

import Spinner from "../components/spinner";

const SingleBuild = props => {
  const { build } = useBuild(props.match.params.id);

  if (!build) return <Spinner mt={8} />;

  const {
    id,
    data: {
      buildResult: { message, status, timestamp },
      pullRequest: { issue_url, number, title, url },
      user: { avatar_url, login }
    },
    type: { action: type }
  } = build;

  return (
    <>
      <Link to="/builds" className="yellow-link">
        Back to builds
      </Link>
      <div className="container">
        <h1 className="page-title">{title}</h1>
        <figure className="big-profile">
          <img src={avatar_url} alt="profile image" />
          <figcaption className="bigger">{login}</figcaption>
        </figure>
        <p className={`bigger ${status ? "success" : "fail"}`}>
          {status ? "Success" : "Fail"}
        </p>
        <p>
          <span className="rubric">ID:</span> {id}
        </p>
        <p>
          <span className="rubric">Datetime: </span>
          {timestamp}
        </p>
        <p>
          <span className="rubric">Message: </span>
          {message}
        </p>
        <a href={issue_url} className="bigger yellow-link" target="_blank">
          View on github
        </a>
      </div>
    </>
  );
};

export default SingleBuild;
