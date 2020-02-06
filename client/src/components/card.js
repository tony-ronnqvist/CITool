import React from "react";
import PropTypes from "prop-types";
import styled from "styled-components";
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";

const StyledCard = styled.div`
  width: 700px;
  /* padding: 1rem; */
  border-radius: 0 4px 4px 0;
  box-shadow: 0 3px 5px -3px rgba(0, 0, 0, 0.5);
  background-color: #fafafa;
  border-left: 8px solid ${props => (props.status ? "#2ecc71" : "#e74c3c")};
  overflow: hidden;
  display: flex;
  align-items: center;
  cursor: pointer;
  transition-property: transform, background-color;
  transition-duration: 0.33s;
  transition-timing-function: ease;
  &:hover {
    transform: scale(1.03);
    background-color: #f4f4f4;
  }

  .outer-wrapper {
    display: flex;
    padding: 1rem;
    &:first-child {
      border-right: 1px solid #ddd;
      display: flex;
      flex: 0.6;
    }
    .info-wrapper {
      width: 80px;
      margin-right: 2rem;
      .pr-title {
        color: ${props => (props.status ? "#2ecc71" : "#e74c3c")};
        font-weight: bold;
        margin-bottom: 0.5rem;
        text-align: left;
      }

      figure {
        display: flex;

        img {
          height: auto;
          width: 15px;
          margin-right: 0.35rem;
          border-radius: 50%;
        }
      }
    }

    .title-wrapper {
      color: #1d1d1d;
    }
  }
`;

// TODO: Card does not need all this info. Slim down to essentials
const card = ({ timestamp, status, number, title, avatar_url, login }) => {
  dayjs.extend(relativeTime);
  return (
    <StyledCard status={status}>
      <div className="outer-wrapper">
        <div className="info-wrapper">
          <div className="pr-title">PR #{number}</div>
          <figure>
            <img src={avatar_url} alt="user avatar" />
            <figcaption>{login}</figcaption>
          </figure>
        </div>
        <div className="title-wrapper">{title}</div>
      </div>
      <div className="outer-wrapper">
        {/* <div>{dayjs(updated_at || closed_at || merged_at).fromNow()}</div> */}
        <div></div>
      </div>
    </StyledCard>
  );
};

// pullrequest: { issue_url, number, title, url },
//   user: { avatar_url, login }

const { number: pNumber, string: pString, bool: pBool } = PropTypes;

card.propTypes = {
  timestamp: pString.isRequired,
  status: pBool.isRequired,
  number: pNumber.isRequired,
  title: pString.isRequired,
  avatar_url: pString.isRequired,
  login: pString.isRequired
};

export default card;
