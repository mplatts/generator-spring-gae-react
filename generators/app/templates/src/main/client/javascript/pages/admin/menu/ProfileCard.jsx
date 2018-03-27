import React from 'react';
import Avatar from 'react-avatar';
import { object } from 'prop-types';
import { Card } from 'material-ui';
import './ProfileCard.less';

const ProfileCard = ({ user }) => {
  const { email, name } = user || {};

  return (
    <Card className="profile-card">
      <div className="profile-details">
        <Avatar
          className="avatar"
          name={name}
          email={email}
          size={48}
          round
        />
        <div className="name">
          {name || email}
        </div>
        <div className="email">
          {name && email}
        </div>
      </div>
    </Card>
  );
};

ProfileCard.propTypes = {
  user: object,
};

ProfileCard.defaultProps = {
  user: {},
};

export default ProfileCard;
