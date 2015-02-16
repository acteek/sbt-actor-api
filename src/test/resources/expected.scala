package im.actor.api {
  package auth {
    case class RequestSendAuthCode(phoneNumber: Long, appId: Int, apiKey: String) extends RpcRequest
    object RequestSendAuthCode {
      val header = 1
      val Response = Refs.ResponseSendAuthCode
      case class Partial(optphoneNumber: Option[Long], optappId: Option[Int], optapiKey: Option[String]) {
        def toComplete: Option[RequestSendAuthCode] = {
          for {
            phoneNumber <- optphoneNumber
            appId <- optappId
            apiKey <- optapiKey
          } yield RequestSendAuthCode(phoneNumber, appId, apiKey)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSendAuthCode] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optphoneNumber = Some(in.readInt64())))
            }
            case 16 => {
              doParse(partialMessage.copy(optappId = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(optapiKey = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseSendAuthCode(smsHash: String, isRegistered: Boolean) extends RpcResponse
    object ResponseSendAuthCode {
      val header = 2
      case class Partial(optsmsHash: Option[String], optisRegistered: Option[Boolean]) {
        def toComplete: Option[ResponseSendAuthCode] = {
          for {
            smsHash <- optsmsHash
            isRegistered <- optisRegistered
          } yield ResponseSendAuthCode(smsHash, isRegistered)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseSendAuthCode] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(optsmsHash = Some(in.readString())))
            }
            case 16 => {
              doParse(partialMessage.copy(optisRegistered = Some(in.readBool())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSendAuthCall(phoneNumber: Long, smsHash: String, appId: Int, apiKey: String) extends RpcRequest
    object RequestSendAuthCall {
      val header = 90
      val Response = Refs.ResponseVoid
      case class Partial(optphoneNumber: Option[Long], optsmsHash: Option[String], optappId: Option[Int], optapiKey: Option[String]) {
        def toComplete: Option[RequestSendAuthCall] = {
          for {
            phoneNumber <- optphoneNumber
            smsHash <- optsmsHash
            appId <- optappId
            apiKey <- optapiKey
          } yield RequestSendAuthCall(phoneNumber, smsHash, appId, apiKey)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSendAuthCall] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optphoneNumber = Some(in.readInt64())))
            }
            case 18 => {
              doParse(partialMessage.copy(optsmsHash = Some(in.readString())))
            }
            case 24 => {
              doParse(partialMessage.copy(optappId = Some(in.readInt32())))
            }
            case 34 => {
              doParse(partialMessage.copy(optapiKey = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseAuth(publicKeyHash: Long, user: Refs.User, config: Refs.Config) extends RpcResponse
    object ResponseAuth {
      val header = 5
      case class Partial(optpublicKeyHash: Option[Long], eitheruser: Either[Refs.User.Partial, Refs.User], eitherconfig: Either[Refs.Config.Partial, Refs.Config]) {
        def toComplete: Option[ResponseAuth] = {
          for {
            publicKeyHash <- optpublicKeyHash
            user <- eitheruser.right.toOption
            config <- eitherconfig.right.toOption
          } yield ResponseAuth(publicKeyHash, user, config)
        }
      }
      object Partial {
        val empty = Partial.apply(None, Left(Refs.User.Partial.empty), Left(Refs.Config.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseAuth] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optpublicKeyHash = Some(in.readInt64())))
            }
            case 18 => {
              doParse(partialMessage.copy(eitheruser = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.User.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 26 => {
              doParse(partialMessage.copy(eitherconfig = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Config.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSignIn(phoneNumber: Long, smsHash: String, smsCode: String, publicKey: Array[Byte], deviceHash: Array[Byte], deviceTitle: String, appId: Int, appKey: String) extends RpcRequest
    object RequestSignIn {
      val header = 3
      val Response = Refs.ResponseAuth
      case class Partial(optphoneNumber: Option[Long], optsmsHash: Option[String], optsmsCode: Option[String], optpublicKey: Option[Array[Byte]], optdeviceHash: Option[Array[Byte]], optdeviceTitle: Option[String], optappId: Option[Int], optappKey: Option[String]) {
        def toComplete: Option[RequestSignIn] = {
          for {
            phoneNumber <- optphoneNumber
            smsHash <- optsmsHash
            smsCode <- optsmsCode
            publicKey <- optpublicKey
            deviceHash <- optdeviceHash
            deviceTitle <- optdeviceTitle
            appId <- optappId
            appKey <- optappKey
          } yield RequestSignIn(phoneNumber, smsHash, smsCode, publicKey, deviceHash, deviceTitle, appId, appKey)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None, None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSignIn] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optphoneNumber = Some(in.readInt64())))
            }
            case 18 => {
              doParse(partialMessage.copy(optsmsHash = Some(in.readString())))
            }
            case 26 => {
              doParse(partialMessage.copy(optsmsCode = Some(in.readString())))
            }
            case 34 => {
              doParse(partialMessage.copy(optpublicKey = Some(in.readBytes().toByteArray())))
            }
            case 42 => {
              doParse(partialMessage.copy(optdeviceHash = Some(in.readBytes().toByteArray())))
            }
            case 50 => {
              doParse(partialMessage.copy(optdeviceTitle = Some(in.readString())))
            }
            case 56 => {
              doParse(partialMessage.copy(optappId = Some(in.readInt32())))
            }
            case 66 => {
              doParse(partialMessage.copy(optappKey = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSignUp(phoneNumber: Long, smsHash: String, smsCode: String, name: String, publicKey: Array[Byte], deviceHash: Array[Byte], deviceTitle: String, appId: Int, appKey: String, isSilent: Boolean) extends RpcRequest
    object RequestSignUp {
      val header = 4
      val Response = Refs.ResponseAuth
      case class Partial(optphoneNumber: Option[Long], optsmsHash: Option[String], optsmsCode: Option[String], optname: Option[String], optpublicKey: Option[Array[Byte]], optdeviceHash: Option[Array[Byte]], optdeviceTitle: Option[String], optappId: Option[Int], optappKey: Option[String], optisSilent: Option[Boolean]) {
        def toComplete: Option[RequestSignUp] = {
          for {
            phoneNumber <- optphoneNumber
            smsHash <- optsmsHash
            smsCode <- optsmsCode
            name <- optname
            publicKey <- optpublicKey
            deviceHash <- optdeviceHash
            deviceTitle <- optdeviceTitle
            appId <- optappId
            appKey <- optappKey
            isSilent <- optisSilent
          } yield RequestSignUp(phoneNumber, smsHash, smsCode, name, publicKey, deviceHash, deviceTitle, appId, appKey, isSilent)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None, None, None, None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSignUp] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optphoneNumber = Some(in.readInt64())))
            }
            case 18 => {
              doParse(partialMessage.copy(optsmsHash = Some(in.readString())))
            }
            case 26 => {
              doParse(partialMessage.copy(optsmsCode = Some(in.readString())))
            }
            case 34 => {
              doParse(partialMessage.copy(optname = Some(in.readString())))
            }
            case 50 => {
              doParse(partialMessage.copy(optpublicKey = Some(in.readBytes().toByteArray())))
            }
            case 58 => {
              doParse(partialMessage.copy(optdeviceHash = Some(in.readBytes().toByteArray())))
            }
            case 66 => {
              doParse(partialMessage.copy(optdeviceTitle = Some(in.readString())))
            }
            case 72 => {
              doParse(partialMessage.copy(optappId = Some(in.readInt32())))
            }
            case 82 => {
              doParse(partialMessage.copy(optappKey = Some(in.readString())))
            }
            case 88 => {
              doParse(partialMessage.copy(optisSilent = Some(in.readBool())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class AuthSession(id: Int, authHolder: Int, appId: Int, appTitle: String, deviceTitle: String, authTime: Int, authLocation: String, latitude: Option[Double], longitude: Option[Double])
    object AuthSession {
      case class Partial(optid: Option[Int], optauthHolder: Option[Int], optappId: Option[Int], optappTitle: Option[String], optdeviceTitle: Option[String], optauthTime: Option[Int], optauthLocation: Option[String], optlatitude: Option[Option[Double]], optlongitude: Option[Option[Double]]) {
        def toComplete: Option[AuthSession] = {
          for {
            id <- optid
            authHolder <- optauthHolder
            appId <- optappId
            appTitle <- optappTitle
            deviceTitle <- optdeviceTitle
            authTime <- optauthTime
            authLocation <- optauthLocation
            latitude <- optlatitude
            longitude <- optlongitude
          } yield AuthSession(id, authHolder, appId, appTitle, deviceTitle, authTime, authLocation, latitude, longitude)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None, None, None, None, Some(None), Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, AuthSession] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optauthHolder = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optappId = Some(in.readInt32())))
            }
            case 34 => {
              doParse(partialMessage.copy(optappTitle = Some(in.readString())))
            }
            case 42 => {
              doParse(partialMessage.copy(optdeviceTitle = Some(in.readString())))
            }
            case 48 => {
              doParse(partialMessage.copy(optauthTime = Some(in.readInt32())))
            }
            case 58 => {
              doParse(partialMessage.copy(optauthLocation = Some(in.readString())))
            }
            case 65 => {
              doParse(partialMessage.copy(optlatitude = Some(Some(in.readDouble()))))
            }
            case 73 => {
              doParse(partialMessage.copy(optlongitude = Some(Some(in.readDouble()))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    trait RequestGetAuthSessions extends RpcRequest
    case object RequestGetAuthSessions extends RequestGetAuthSessions {
      val header = 80
      val Response = Refs.ResponseGetAuthSessions
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Unit, RequestGetAuthSessions] = {
        def doParse: Unit = {
          in.readTag() match {
            case 0 => {
              ()
            }
            case default => if (in.skipField(default) == true) doParse
            else {
              ()
            }
          }
        }
        doParse
        Right(RequestGetAuthSessions)
      }
    }
    case class ResponseGetAuthSessions(userAuths: Vector[Refs.AuthSession]) extends RpcResponse
    object ResponseGetAuthSessions {
      val header = 81
      case class Partial(eithersuserAuths: Vector[Either[Refs.AuthSession.Partial, Refs.AuthSession]]) {
        def toComplete: Option[ResponseGetAuthSessions] = {
          for (userAuths <- {
            val eitherMsgsView = eithersuserAuths.partition(_.isLeft) match {
              case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                yield msg)
              case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                yield partialMsg)
            }
            eitherMsgsView match {
              case Right(msgs) => Some(msgs.force.toVector)
              case Left(partialMsgs) => None
            }
          })
            yield ResponseGetAuthSessions(userAuths)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseGetAuthSessions] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersuserAuths = partialMessage.eithersuserAuths :+ {
                Refs.AuthSession.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestTerminateSession(id: Int) extends RpcRequest
    object RequestTerminateSession {
      val header = 82
      val Response = Refs.ResponseVoid
      case class Partial(optid: Option[Int]) {
        def toComplete: Option[RequestTerminateSession] = {
          for (id <- optid)
            yield RequestTerminateSession(id)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestTerminateSession] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optid = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    trait RequestTerminateAllSessions extends RpcRequest
    case object RequestTerminateAllSessions extends RequestTerminateAllSessions {
      val header = 83
      val Response = Refs.ResponseVoid
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Unit, RequestTerminateAllSessions] = {
        def doParse: Unit = {
          in.readTag() match {
            case 0 => {
              ()
            }
            case default => if (in.skipField(default) == true) doParse
            else {
              ()
            }
          }
        }
        doParse
        Right(RequestTerminateAllSessions)
      }
    }
    trait RequestSignOut extends RpcRequest
    case object RequestSignOut extends RequestSignOut {
      val header = 84
      val Response = Refs.ResponseVoid
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Unit, RequestSignOut] = {
        def doParse: Unit = {
          in.readTag() match {
            case 0 => {
              ()
            }
            case default => if (in.skipField(default) == true) doParse
            else {
              ()
            }
          }
        }
        doParse
        Right(RequestSignOut)
      }
    }
  }
  package users {
    trait Sex
    object Sex extends Enumeration with Sex {
      type Sex = Value
      val Unknown: Sex = Value(1)
      val Male: Sex = Value(2)
      val Female: Sex = Value(3)
    }
    trait UserState
    object UserState extends Enumeration with UserState {
      type UserState = Value
      val Registered: UserState = Value(1)
      val Email: UserState = Value(2)
      val Deleted: UserState = Value(3)
    }
    case class Phone(id: Int, accessHash: Long, phone: Long, phoneTitle: String)
    object Phone {
      case class Partial(optid: Option[Int], optaccessHash: Option[Long], optphone: Option[Long], optphoneTitle: Option[String]) {
        def toComplete: Option[Phone] = {
          for {
            id <- optid
            accessHash <- optaccessHash
            phone <- optphone
            phoneTitle <- optphoneTitle
          } yield Phone(id, accessHash, phone, phoneTitle)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, Phone] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 24 => {
              doParse(partialMessage.copy(optphone = Some(in.readInt64())))
            }
            case 34 => {
              doParse(partialMessage.copy(optphoneTitle = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class Email(id: Int, accessHash: Long, email: String, emailTitle: String)
    object Email {
      case class Partial(optid: Option[Int], optaccessHash: Option[Long], optemail: Option[String], optemailTitle: Option[String]) {
        def toComplete: Option[Email] = {
          for {
            id <- optid
            accessHash <- optaccessHash
            email <- optemail
            emailTitle <- optemailTitle
          } yield Email(id, accessHash, email, emailTitle)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, Email] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 26 => {
              doParse(partialMessage.copy(optemail = Some(in.readString())))
            }
            case 34 => {
              doParse(partialMessage.copy(optemailTitle = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class User(id: Int, accessHash: Long, name: String, localName: Option[String], sex: Option[Refs.Sex], keyHashes: Vector[Long], phone: Long, avatar: Option[Refs.Avatar], phones: Vector[Int], emails: Vector[Int], userState: Refs.UserState)
    object User {
      case class Partial(optid: Option[Int], optaccessHash: Option[Long], optname: Option[String], optlocalName: Option[Option[String]], optsex: Option[Option[Refs.Sex]], keyHashes: Vector[Long], optphone: Option[Long], opteitheravatar: Option[Option[Either[Refs.Avatar.Partial, Refs.Avatar]]], phones: Vector[Int], emails: Vector[Int], optuserState: Option[Refs.UserState]) {
        def toComplete: Option[User] = {
          for {
            id <- optid
            accessHash <- optaccessHash
            name <- optname
            localName <- optlocalName
            sex <- optsex
            phone <- optphone
            avatar <- {
              opteitheravatar match {
                case None => None
                case Some(Some(Left(_))) => None
                case Some(None) => Some(None)
                case Some(Some(Right(msg))) => Some(Some(msg))
              }
            }
            userState <- optuserState
          } yield User(id, accessHash, name, localName, sex, keyHashes, phone, avatar, phones, emails, userState)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, Some(None), Some(None), Vector.empty, None, Some(None), Vector.empty, Vector.empty, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, User] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 26 => {
              doParse(partialMessage.copy(optname = Some(in.readString())))
            }
            case 34 => {
              doParse(partialMessage.copy(optlocalName = Some(Some(in.readString()))))
            }
            case 40 => {
              doParse(partialMessage.copy(optsex = Some(Some({
                Refs.Sex(in.readEnum())
              }))))
            }
            case 48 => {
              doParse(partialMessage.copy(keyHashes = partialMessage.keyHashes :+ in.readInt64()))
            }
            case 50 => {
              doParse(partialMessage.copy(keyHashes = partialMessage.keyHashes ++ {
                val length = in.readRawVarint32()
                val limit = in.pushLimit(length)
                val values = Iterator.continually(in.readInt64()).takeWhile(_ => in.getBytesUntilLimit() > 0).toVector
                in.popLimit(limit)
                values
              }))
            }
            case 56 => {
              doParse(partialMessage.copy(optphone = Some(in.readInt64())))
            }
            case 66 => {
              doParse(partialMessage.copy(opteitheravatar = Some(Some({
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Avatar.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))))
            }
            case 72 => {
              doParse(partialMessage.copy(phones = partialMessage.phones :+ in.readInt32()))
            }
            case 74 => {
              doParse(partialMessage.copy(phones = partialMessage.phones ++ {
                val length = in.readRawVarint32()
                val limit = in.pushLimit(length)
                val values = Iterator.continually(in.readInt32()).takeWhile(_ => in.getBytesUntilLimit() > 0).toVector
                in.popLimit(limit)
                values
              }))
            }
            case 80 => {
              doParse(partialMessage.copy(emails = partialMessage.emails :+ in.readInt32()))
            }
            case 82 => {
              doParse(partialMessage.copy(emails = partialMessage.emails ++ {
                val length = in.readRawVarint32()
                val limit = in.pushLimit(length)
                val values = Iterator.continually(in.readInt32()).takeWhile(_ => in.getBytesUntilLimit() > 0).toVector
                in.popLimit(limit)
                values
              }))
            }
            case 88 => {
              doParse(partialMessage.copy(optuserState = Some({
                Refs.UserState(in.readEnum())
              })))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestEditUserLocalName(uid: Int, accessHash: Long, name: String) extends RpcRequest
    object RequestEditUserLocalName {
      val header = 96
      val Response = Refs.ResponseSeq
      case class Partial(optuid: Option[Int], optaccessHash: Option[Long], optname: Option[String]) {
        def toComplete: Option[RequestEditUserLocalName] = {
          for {
            uid <- optuid
            accessHash <- optaccessHash
            name <- optname
          } yield RequestEditUserLocalName(uid, accessHash, name)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestEditUserLocalName] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 26 => {
              doParse(partialMessage.copy(optname = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserAvatarChanged(uid: Int, avatar: Option[Refs.Avatar]) extends Update
    object UpdateUserAvatarChanged {
      val header = 16
      case class Partial(optuid: Option[Int], opteitheravatar: Option[Option[Either[Refs.Avatar.Partial, Refs.Avatar]]]) {
        def toComplete: Option[UpdateUserAvatarChanged] = {
          for {
            uid <- optuid
            avatar <- {
              opteitheravatar match {
                case None => None
                case Some(Some(Left(_))) => None
                case Some(None) => Some(None)
                case Some(Some(Right(msg))) => Some(Some(msg))
              }
            }
          } yield UpdateUserAvatarChanged(uid, avatar)
        }
      }
      object Partial {
        val empty = Partial.apply(None, Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserAvatarChanged] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(opteitheravatar = Some(Some({
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Avatar.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserNameChanged(uid: Int, name: String) extends Update
    object UpdateUserNameChanged {
      val header = 32
      case class Partial(optuid: Option[Int], optname: Option[String]) {
        def toComplete: Option[UpdateUserNameChanged] = {
          for {
            uid <- optuid
            name <- optname
          } yield UpdateUserNameChanged(uid, name)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserNameChanged] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(optname = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserLocalNameChanged(uid: Int, localName: Option[String]) extends Update
    object UpdateUserLocalNameChanged {
      val header = 51
      case class Partial(optuid: Option[Int], optlocalName: Option[Option[String]]) {
        def toComplete: Option[UpdateUserLocalNameChanged] = {
          for {
            uid <- optuid
            localName <- optlocalName
          } yield UpdateUserLocalNameChanged(uid, localName)
        }
      }
      object Partial {
        val empty = Partial.apply(None, Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserLocalNameChanged] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(optlocalName = Some(Some(in.readString()))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserPhoneAdded(uid: Int, phoneId: Int) extends Update
    object UpdateUserPhoneAdded {
      val header = 87
      case class Partial(optuid: Option[Int], optphoneId: Option[Int]) {
        def toComplete: Option[UpdateUserPhoneAdded] = {
          for {
            uid <- optuid
            phoneId <- optphoneId
          } yield UpdateUserPhoneAdded(uid, phoneId)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserPhoneAdded] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optphoneId = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserPhoneRemoved(uid: Int, phoneId: Int) extends Update
    object UpdateUserPhoneRemoved {
      val header = 88
      case class Partial(optuid: Option[Int], optphoneId: Option[Int]) {
        def toComplete: Option[UpdateUserPhoneRemoved] = {
          for {
            uid <- optuid
            phoneId <- optphoneId
          } yield UpdateUserPhoneRemoved(uid, phoneId)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserPhoneRemoved] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optphoneId = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdatePhoneTitleChanged(phoneId: Int, title: String) extends Update
    object UpdatePhoneTitleChanged {
      val header = 89
      case class Partial(optphoneId: Option[Int], opttitle: Option[String]) {
        def toComplete: Option[UpdatePhoneTitleChanged] = {
          for {
            phoneId <- optphoneId
            title <- opttitle
          } yield UpdatePhoneTitleChanged(phoneId, title)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdatePhoneTitleChanged] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 16 => {
              doParse(partialMessage.copy(optphoneId = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(opttitle = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdatePhoneMoved(phoneId: Int, uid: Int) extends Update
    object UpdatePhoneMoved {
      val header = 101
      case class Partial(optphoneId: Option[Int], optuid: Option[Int]) {
        def toComplete: Option[UpdatePhoneMoved] = {
          for {
            phoneId <- optphoneId
            uid <- optuid
          } yield UpdatePhoneMoved(phoneId, uid)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdatePhoneMoved] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optphoneId = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserEmailAdded(uid: Int, emailId: Int) extends Update
    object UpdateUserEmailAdded {
      val header = 96
      case class Partial(optuid: Option[Int], optemailId: Option[Int]) {
        def toComplete: Option[UpdateUserEmailAdded] = {
          for {
            uid <- optuid
            emailId <- optemailId
          } yield UpdateUserEmailAdded(uid, emailId)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserEmailAdded] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optemailId = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserEmailRemoved(uid: Int, emailId: Int) extends Update
    object UpdateUserEmailRemoved {
      val header = 97
      case class Partial(optuid: Option[Int], optemailId: Option[Int]) {
        def toComplete: Option[UpdateUserEmailRemoved] = {
          for {
            uid <- optuid
            emailId <- optemailId
          } yield UpdateUserEmailRemoved(uid, emailId)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserEmailRemoved] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optemailId = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateEmailTitleChanged(emailId: Int, title: String) extends Update
    object UpdateEmailTitleChanged {
      val header = 98
      case class Partial(optemailId: Option[Int], opttitle: Option[String]) {
        def toComplete: Option[UpdateEmailTitleChanged] = {
          for {
            emailId <- optemailId
            title <- opttitle
          } yield UpdateEmailTitleChanged(emailId, title)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateEmailTitleChanged] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optemailId = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(opttitle = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateEmailMoved(emailId: Int, uid: Int) extends Update
    object UpdateEmailMoved {
      val header = 102
      case class Partial(optemailId: Option[Int], optuid: Option[Int]) {
        def toComplete: Option[UpdateEmailMoved] = {
          for {
            emailId <- optemailId
            uid <- optuid
          } yield UpdateEmailMoved(emailId, uid)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateEmailMoved] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optemailId = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserContactsChanged(uid: Int, phones: Vector[Int], emails: Vector[Int]) extends Update
    object UpdateUserContactsChanged {
      val header = 86
      case class Partial(optuid: Option[Int], phones: Vector[Int], emails: Vector[Int]) {
        def toComplete: Option[UpdateUserContactsChanged] = {
          for (uid <- optuid)
            yield UpdateUserContactsChanged(uid, phones, emails)
        }
      }
      object Partial {
        val empty = Partial.apply(None, Vector.empty, Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserContactsChanged] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(phones = partialMessage.phones :+ in.readInt32()))
            }
            case 18 => {
              doParse(partialMessage.copy(phones = partialMessage.phones ++ {
                val length = in.readRawVarint32()
                val limit = in.pushLimit(length)
                val values = Iterator.continually(in.readInt32()).takeWhile(_ => in.getBytesUntilLimit() > 0).toVector
                in.popLimit(limit)
                values
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(emails = partialMessage.emails :+ in.readInt32()))
            }
            case 26 => {
              doParse(partialMessage.copy(emails = partialMessage.emails ++ {
                val length = in.readRawVarint32()
                val limit = in.pushLimit(length)
                val values = Iterator.continually(in.readInt32()).takeWhile(_ => in.getBytesUntilLimit() > 0).toVector
                in.popLimit(limit)
                values
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserStateChanged(uid: Int, state: Refs.UserState) extends Update
    object UpdateUserStateChanged {
      val header = 100
      case class Partial(optuid: Option[Int], optstate: Option[Refs.UserState]) {
        def toComplete: Option[UpdateUserStateChanged] = {
          for {
            uid <- optuid
            state <- optstate
          } yield UpdateUserStateChanged(uid, state)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserStateChanged] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optstate = Some({
                Refs.UserState(in.readEnum())
              })))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  package profile {
    case class RequestEditName(name: String) extends RpcRequest
    object RequestEditName {
      val header = 53
      val Response = Refs.ResponseSeq
      case class Partial(optname: Option[String]) {
        def toComplete: Option[RequestEditName] = {
          for (name <- optname)
            yield RequestEditName(name)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestEditName] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(optname = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestEditAvatar(fileLocation: Refs.FileLocation) extends RpcRequest
    object RequestEditAvatar {
      val header = 31
      val Response = Refs.ResponseEditAvatar
      case class Partial(eitherfileLocation: Either[Refs.FileLocation.Partial, Refs.FileLocation]) {
        def toComplete: Option[RequestEditAvatar] = {
          for (fileLocation <- eitherfileLocation.right.toOption)
            yield RequestEditAvatar(fileLocation)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.FileLocation.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestEditAvatar] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherfileLocation = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.FileLocation.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseEditAvatar(avatar: Refs.Avatar, seq: Int, state: Array[Byte]) extends RpcResponse
    object ResponseEditAvatar {
      val header = 103
      case class Partial(eitheravatar: Either[Refs.Avatar.Partial, Refs.Avatar], optseq: Option[Int], optstate: Option[Array[Byte]]) {
        def toComplete: Option[ResponseEditAvatar] = {
          for {
            avatar <- eitheravatar.right.toOption
            seq <- optseq
            state <- optstate
          } yield ResponseEditAvatar(avatar, seq, state)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Avatar.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseEditAvatar] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitheravatar = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Avatar.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optseq = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(optstate = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    trait RequestRemoveAvatar extends RpcRequest
    case object RequestRemoveAvatar extends RequestRemoveAvatar {
      val header = 91
      val Response = Refs.ResponseSeq
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Unit, RequestRemoveAvatar] = {
        def doParse: Unit = {
          in.readTag() match {
            case 0 => {
              ()
            }
            case default => if (in.skipField(default) == true) doParse
            else {
              ()
            }
          }
        }
        doParse
        Right(RequestRemoveAvatar)
      }
    }
    case class RequestSendEmailCode(email: String, description: Option[String]) extends RpcRequest
    object RequestSendEmailCode {
      val header = 120
      val Response = Refs.ResponseVoid
      case class Partial(optemail: Option[String], optdescription: Option[Option[String]]) {
        def toComplete: Option[RequestSendEmailCode] = {
          for {
            email <- optemail
            description <- optdescription
          } yield RequestSendEmailCode(email, description)
        }
      }
      object Partial {
        val empty = Partial.apply(None, Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSendEmailCode] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(optemail = Some(in.readString())))
            }
            case 18 => {
              doParse(partialMessage.copy(optdescription = Some(Some(in.readString()))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestDetachEmail(email: Int, accessHash: Long) extends RpcRequest
    object RequestDetachEmail {
      val header = 123
      val Response = Refs.ResponseSeq
      case class Partial(optemail: Option[Int], optaccessHash: Option[Long]) {
        def toComplete: Option[RequestDetachEmail] = {
          for {
            email <- optemail
            accessHash <- optaccessHash
          } yield RequestDetachEmail(email, accessHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestDetachEmail] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optemail = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestChangePhoneTitle(phoneId: Int, title: String) extends RpcRequest
    object RequestChangePhoneTitle {
      val header = 124
      val Response = Refs.ResponseSeq
      case class Partial(optphoneId: Option[Int], opttitle: Option[String]) {
        def toComplete: Option[RequestChangePhoneTitle] = {
          for {
            phoneId <- optphoneId
            title <- opttitle
          } yield RequestChangePhoneTitle(phoneId, title)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestChangePhoneTitle] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optphoneId = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(opttitle = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestChangeEmailTitle(emailId: Int, title: String) extends RpcRequest
    object RequestChangeEmailTitle {
      val header = 125
      val Response = Refs.ResponseSeq
      case class Partial(optemailId: Option[Int], opttitle: Option[String]) {
        def toComplete: Option[RequestChangeEmailTitle] = {
          for {
            emailId <- optemailId
            title <- opttitle
          } yield RequestChangeEmailTitle(emailId, title)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestChangeEmailTitle] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optemailId = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(opttitle = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  package contacts {
    case class PhoneToImport(phoneNumber: Long, name: Option[String])
    object PhoneToImport {
      case class Partial(optphoneNumber: Option[Long], optname: Option[Option[String]]) {
        def toComplete: Option[PhoneToImport] = {
          for {
            phoneNumber <- optphoneNumber
            name <- optname
          } yield PhoneToImport(phoneNumber, name)
        }
      }
      object Partial {
        val empty = Partial.apply(None, Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, PhoneToImport] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optphoneNumber = Some(in.readInt64())))
            }
            case 18 => {
              doParse(partialMessage.copy(optname = Some(Some(in.readString()))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class EmailToImport(email: String, name: Option[String])
    object EmailToImport {
      case class Partial(optemail: Option[String], optname: Option[Option[String]]) {
        def toComplete: Option[EmailToImport] = {
          for {
            email <- optemail
            name <- optname
          } yield EmailToImport(email, name)
        }
      }
      object Partial {
        val empty = Partial.apply(None, Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, EmailToImport] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(optemail = Some(in.readString())))
            }
            case 18 => {
              doParse(partialMessage.copy(optname = Some(Some(in.readString()))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestImportContacts(phones: Vector[Refs.PhoneToImport], emails: Vector[Refs.EmailToImport]) extends RpcRequest
    object RequestImportContacts {
      val header = 7
      val Response = Refs.ResponseImportContacts
      case class Partial(eithersphones: Vector[Either[Refs.PhoneToImport.Partial, Refs.PhoneToImport]], eithersemails: Vector[Either[Refs.EmailToImport.Partial, Refs.EmailToImport]]) {
        def toComplete: Option[RequestImportContacts] = {
          for {
            phones <- {
              val eitherMsgsView = eithersphones.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            emails <- {
              val eitherMsgsView = eithersemails.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
          } yield RequestImportContacts(phones, emails)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty, Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestImportContacts] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersphones = partialMessage.eithersphones :+ {
                Refs.PhoneToImport.parseFrom(in)
              }))
            }
            case 18 => {
              doParse(partialMessage.copy(eithersemails = partialMessage.eithersemails :+ {
                Refs.EmailToImport.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseImportContacts(users: Vector[Refs.User], seq: Int, state: Array[Byte]) extends RpcResponse
    object ResponseImportContacts {
      val header = 8
      case class Partial(eithersusers: Vector[Either[Refs.User.Partial, Refs.User]], optseq: Option[Int], optstate: Option[Array[Byte]]) {
        def toComplete: Option[ResponseImportContacts] = {
          for {
            users <- {
              val eitherMsgsView = eithersusers.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            seq <- optseq
            state <- optstate
          } yield ResponseImportContacts(users, seq, state)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseImportContacts] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersusers = partialMessage.eithersusers :+ {
                Refs.User.parseFrom(in)
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optseq = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(optstate = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestGetContacts(contactsHash: String) extends RpcRequest
    object RequestGetContacts {
      val header = 87
      val Response = Refs.ResponseGetContacts
      case class Partial(optcontactsHash: Option[String]) {
        def toComplete: Option[RequestGetContacts] = {
          for (contactsHash <- optcontactsHash)
            yield RequestGetContacts(contactsHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestGetContacts] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(optcontactsHash = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseGetContacts(users: Vector[Refs.User], isNotChanged: Boolean) extends RpcResponse
    object ResponseGetContacts {
      val header = 88
      case class Partial(eithersusers: Vector[Either[Refs.User.Partial, Refs.User]], optisNotChanged: Option[Boolean]) {
        def toComplete: Option[ResponseGetContacts] = {
          for {
            users <- {
              val eitherMsgsView = eithersusers.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            isNotChanged <- optisNotChanged
          } yield ResponseGetContacts(users, isNotChanged)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseGetContacts] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersusers = partialMessage.eithersusers :+ {
                Refs.User.parseFrom(in)
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optisNotChanged = Some(in.readBool())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestRemoveContact(uid: Int, accessHash: Long) extends RpcRequest
    object RequestRemoveContact {
      val header = 89
      val Response = Refs.ResponseSeq
      case class Partial(optuid: Option[Int], optaccessHash: Option[Long]) {
        def toComplete: Option[RequestRemoveContact] = {
          for {
            uid <- optuid
            accessHash <- optaccessHash
          } yield RequestRemoveContact(uid, accessHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestRemoveContact] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestAddContact(uid: Int, accessHash: Long) extends RpcRequest
    object RequestAddContact {
      val header = 114
      val Response = Refs.ResponseSeq
      case class Partial(optuid: Option[Int], optaccessHash: Option[Long]) {
        def toComplete: Option[RequestAddContact] = {
          for {
            uid <- optuid
            accessHash <- optaccessHash
          } yield RequestAddContact(uid, accessHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestAddContact] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSearchContacts(request: String) extends RpcRequest
    object RequestSearchContacts {
      val header = 112
      val Response = Refs.ResponseSearchContacts
      case class Partial(optrequest: Option[String]) {
        def toComplete: Option[RequestSearchContacts] = {
          for (request <- optrequest)
            yield RequestSearchContacts(request)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSearchContacts] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(optrequest = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseSearchContacts(users: Vector[Refs.User]) extends RpcResponse
    object ResponseSearchContacts {
      val header = 113
      case class Partial(eithersusers: Vector[Either[Refs.User.Partial, Refs.User]]) {
        def toComplete: Option[ResponseSearchContacts] = {
          for (users <- {
            val eitherMsgsView = eithersusers.partition(_.isLeft) match {
              case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                yield msg)
              case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                yield partialMsg)
            }
            eitherMsgsView match {
              case Right(msgs) => Some(msgs.force.toVector)
              case Left(partialMsgs) => None
            }
          })
            yield ResponseSearchContacts(users)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseSearchContacts] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersusers = partialMessage.eithersusers :+ {
                Refs.User.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateContactRegistered(uid: Int, isSilent: Boolean, date: Long) extends Update
    object UpdateContactRegistered {
      val header = 5
      case class Partial(optuid: Option[Int], optisSilent: Option[Boolean], optdate: Option[Long]) {
        def toComplete: Option[UpdateContactRegistered] = {
          for {
            uid <- optuid
            isSilent <- optisSilent
            date <- optdate
          } yield UpdateContactRegistered(uid, isSilent, date)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateContactRegistered] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optisSilent = Some(in.readBool())))
            }
            case 24 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateEmailContactRegistered(emailId: Int, uid: Int) extends Update
    object UpdateEmailContactRegistered {
      val header = 120
      case class Partial(optemailId: Option[Int], optuid: Option[Int]) {
        def toComplete: Option[UpdateEmailContactRegistered] = {
          for {
            emailId <- optemailId
            uid <- optuid
          } yield UpdateEmailContactRegistered(emailId, uid)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateEmailContactRegistered] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optemailId = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateContactsAdded(uids: Vector[Int]) extends Update
    object UpdateContactsAdded {
      val header = 40
      case class Partial(uids: Vector[Int]) {
        def toComplete: Option[UpdateContactsAdded] = {
          Some(UpdateContactsAdded(uids))
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateContactsAdded] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(uids = partialMessage.uids :+ in.readInt32()))
            }
            case 10 => {
              doParse(partialMessage.copy(uids = partialMessage.uids ++ {
                val length = in.readRawVarint32()
                val limit = in.pushLimit(length)
                val values = Iterator.continually(in.readInt32()).takeWhile(_ => in.getBytesUntilLimit() > 0).toVector
                in.popLimit(limit)
                values
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateContactsRemoved(uids: Vector[Int]) extends Update
    object UpdateContactsRemoved {
      val header = 41
      case class Partial(uids: Vector[Int]) {
        def toComplete: Option[UpdateContactsRemoved] = {
          Some(UpdateContactsRemoved(uids))
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateContactsRemoved] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(uids = partialMessage.uids :+ in.readInt32()))
            }
            case 10 => {
              doParse(partialMessage.copy(uids = partialMessage.uids ++ {
                val length = in.readRawVarint32()
                val limit = in.pushLimit(length)
                val values = Iterator.continually(in.readInt32()).takeWhile(_ => in.getBytesUntilLimit() > 0).toVector
                in.popLimit(limit)
                values
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  package messaging {
    case class MessageContent(`type`: Int, content: Message)
    object MessageContent {
      case class Partial(opttype: Option[Int], eithercontent: Either[Any, Refs.Message]) {
        def toComplete: Option[MessageContent] = {
          for {
            `type` <- opttype
            content <- eithercontent.right.toOption
          } yield MessageContent(`type`, content)
        }
      }
      object Partial {
        val empty = Partial.apply(None, Left(()))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, MessageContent] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(opttype = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(eithercontent = partialMessage.opttype match {
                case Some(extType) => {
                  val bytes = in.readBytes().toByteArray
                  val stream = com.google.protobuf.CodedInputStream.newInstance(bytes)
                  Refs.Message.parseFrom(stream, extType)
                }
                case None => throw new ParseException("Trying to parse trait but extType is missing")
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class TextMessage(text: String, extType: Int, ext: Option[Array[Byte]]) extends Message
    object TextMessage {
      case class Partial(opttext: Option[String], optextType: Option[Int], optext: Option[Option[Array[Byte]]]) {
        def toComplete: Option[TextMessage] = {
          for {
            text <- opttext
            extType <- optextType
            ext <- optext
          } yield TextMessage(text, extType, ext)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, TextMessage] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(opttext = Some(in.readString())))
            }
            case 16 => {
              doParse(partialMessage.copy(optextType = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(optext = Some(Some(in.readBytes().toByteArray()))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ServiceMessage(text: String, extType: Int, ext: Option[ServiceExtension]) extends Message
    object ServiceMessage {
      case class Partial(opttext: Option[String], optextType: Option[Int], opteitherext: Option[Option[Either[Any, Refs.ServiceExtension]]]) {
        def toComplete: Option[ServiceMessage] = {
          for {
            text <- opttext
            extType <- optextType
            ext <- {
              opteitherext match {
                case None => None
                case Some(Some(Left(_))) => None
                case Some(None) => Some(None)
                case Some(Some(Right(msg))) => Some(Some(msg))
              }
            }
          } yield ServiceMessage(text, extType, ext)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ServiceMessage] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(opttext = Some(in.readString())))
            }
            case 16 => {
              doParse(partialMessage.copy(optextType = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(opteitherext = Some(Some(partialMessage.optextType match {
                case Some(extType) => {
                  val bytes = in.readBytes().toByteArray
                  val stream = com.google.protobuf.CodedInputStream.newInstance(bytes)
                  Refs.ServiceExtension.parseFrom(stream, extType)
                }
                case None => throw new ParseException("Trying to parse trait but extType is missing")
              }))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ServiceExUserAdded(addedUid: Int) extends ServiceExtension
    object ServiceExUserAdded {
      case class Partial(optaddedUid: Option[Int]) {
        def toComplete: Option[ServiceExUserAdded] = {
          for (addedUid <- optaddedUid)
            yield ServiceExUserAdded(addedUid)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ServiceExUserAdded] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optaddedUid = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ServiceExUserKicked(kickedUid: Int) extends ServiceExtension
    object ServiceExUserKicked {
      case class Partial(optkickedUid: Option[Int]) {
        def toComplete: Option[ServiceExUserKicked] = {
          for (kickedUid <- optkickedUid)
            yield ServiceExUserKicked(kickedUid)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ServiceExUserKicked] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optkickedUid = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    trait ServiceExUserLeft extends ServiceExtension
    case object ServiceExUserLeft extends ServiceExUserLeft {
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Unit, ServiceExUserLeft] = {
        def doParse: Unit = {
          in.readTag() match {
            case 0 => {
              ()
            }
            case default => if (in.skipField(default) == true) doParse
            else {
              ()
            }
          }
        }
        doParse
        Right(ServiceExUserLeft)
      }
    }
    trait ServiceExGroupCreated extends ServiceExtension
    case object ServiceExGroupCreated extends ServiceExGroupCreated {
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Unit, ServiceExGroupCreated] = {
        def doParse: Unit = {
          in.readTag() match {
            case 0 => {
              ()
            }
            case default => if (in.skipField(default) == true) doParse
            else {
              ()
            }
          }
        }
        doParse
        Right(ServiceExGroupCreated)
      }
    }
    case class ServiceExChangedTitle(title: String) extends ServiceExtension
    object ServiceExChangedTitle {
      case class Partial(opttitle: Option[String]) {
        def toComplete: Option[ServiceExChangedTitle] = {
          for (title <- opttitle)
            yield ServiceExChangedTitle(title)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ServiceExChangedTitle] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(opttitle = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ServiceExChangedAvatar(avatar: Option[Refs.Avatar]) extends ServiceExtension
    object ServiceExChangedAvatar {
      case class Partial(opteitheravatar: Option[Option[Either[Refs.Avatar.Partial, Refs.Avatar]]]) {
        def toComplete: Option[ServiceExChangedAvatar] = {
          for (avatar <- {
            opteitheravatar match {
              case None => None
              case Some(Some(Left(_))) => None
              case Some(None) => Some(None)
              case Some(Some(Right(msg))) => Some(Some(msg))
            }
          })
            yield ServiceExChangedAvatar(avatar)
        }
      }
      object Partial {
        val empty = Partial.apply(Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ServiceExChangedAvatar] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(opteitheravatar = Some(Some({
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Avatar.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ServiceExEmailContactRegistered(uid: Int) extends ServiceExtension
    object ServiceExEmailContactRegistered {
      case class Partial(optuid: Option[Int]) {
        def toComplete: Option[ServiceExEmailContactRegistered] = {
          for (uid <- optuid)
            yield ServiceExEmailContactRegistered(uid)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ServiceExEmailContactRegistered] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class FileMessage(fileId: Long, accessHash: Long, fileSize: Int, name: String, mimeType: String, thumb: Option[Refs.FastThumb], extType: Int, ext: Option[Array[Byte]]) extends Message
    object FileMessage {
      case class Partial(optfileId: Option[Long], optaccessHash: Option[Long], optfileSize: Option[Int], optname: Option[String], optmimeType: Option[String], opteitherthumb: Option[Option[Either[Refs.FastThumb.Partial, Refs.FastThumb]]], optextType: Option[Int], optext: Option[Option[Array[Byte]]]) {
        def toComplete: Option[FileMessage] = {
          for {
            fileId <- optfileId
            accessHash <- optaccessHash
            fileSize <- optfileSize
            name <- optname
            mimeType <- optmimeType
            thumb <- {
              opteitherthumb match {
                case None => None
                case Some(Some(Left(_))) => None
                case Some(None) => Some(None)
                case Some(Some(Right(msg))) => Some(Some(msg))
              }
            }
            extType <- optextType
            ext <- optext
          } yield FileMessage(fileId, accessHash, fileSize, name, mimeType, thumb, extType, ext)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None, None, Some(None), None, Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, FileMessage] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optfileId = Some(in.readInt64())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 24 => {
              doParse(partialMessage.copy(optfileSize = Some(in.readInt32())))
            }
            case 34 => {
              doParse(partialMessage.copy(optname = Some(in.readString())))
            }
            case 42 => {
              doParse(partialMessage.copy(optmimeType = Some(in.readString())))
            }
            case 50 => {
              doParse(partialMessage.copy(opteitherthumb = Some(Some({
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.FastThumb.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))))
            }
            case 56 => {
              doParse(partialMessage.copy(optextType = Some(in.readInt32())))
            }
            case 66 => {
              doParse(partialMessage.copy(optext = Some(Some(in.readBytes().toByteArray()))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class FileExPhoto(w: Int, h: Int) extends FileExtension
    object FileExPhoto {
      case class Partial(optw: Option[Int], opth: Option[Int]) {
        def toComplete: Option[FileExPhoto] = {
          for {
            w <- optw
            h <- opth
          } yield FileExPhoto(w, h)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, FileExPhoto] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optw = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(opth = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class FileExVideo(w: Int, h: Int, duration: Int) extends FileExtension
    object FileExVideo {
      case class Partial(optw: Option[Int], opth: Option[Int], optduration: Option[Int]) {
        def toComplete: Option[FileExVideo] = {
          for {
            w <- optw
            h <- opth
            duration <- optduration
          } yield FileExVideo(w, h, duration)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, FileExVideo] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optw = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(opth = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optduration = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class FileExVoice(duration: Int) extends FileExtension
    object FileExVoice {
      case class Partial(optduration: Option[Int]) {
        def toComplete: Option[FileExVoice] = {
          for (duration <- optduration)
            yield FileExVoice(duration)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, FileExVoice] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optduration = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class WrongKeysErrorData(newKeys: Vector[Refs.UserKey], removedKeys: Vector[Refs.UserKey], invalidKeys: Vector[Refs.UserKey])
    object WrongKeysErrorData {
      case class Partial(eithersnewKeys: Vector[Either[Refs.UserKey.Partial, Refs.UserKey]], eithersremovedKeys: Vector[Either[Refs.UserKey.Partial, Refs.UserKey]], eithersinvalidKeys: Vector[Either[Refs.UserKey.Partial, Refs.UserKey]]) {
        def toComplete: Option[WrongKeysErrorData] = {
          for {
            newKeys <- {
              val eitherMsgsView = eithersnewKeys.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            removedKeys <- {
              val eitherMsgsView = eithersremovedKeys.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            invalidKeys <- {
              val eitherMsgsView = eithersinvalidKeys.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
          } yield WrongKeysErrorData(newKeys, removedKeys, invalidKeys)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty, Vector.empty, Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, WrongKeysErrorData] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersnewKeys = partialMessage.eithersnewKeys :+ {
                Refs.UserKey.parseFrom(in)
              }))
            }
            case 18 => {
              doParse(partialMessage.copy(eithersremovedKeys = partialMessage.eithersremovedKeys :+ {
                Refs.UserKey.parseFrom(in)
              }))
            }
            case 26 => {
              doParse(partialMessage.copy(eithersinvalidKeys = partialMessage.eithersinvalidKeys :+ {
                Refs.UserKey.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class EncryptedAesKey(keyHash: Long, aesEncryptedKey: Array[Byte])
    object EncryptedAesKey {
      case class Partial(optkeyHash: Option[Long], optaesEncryptedKey: Option[Array[Byte]]) {
        def toComplete: Option[EncryptedAesKey] = {
          for {
            keyHash <- optkeyHash
            aesEncryptedKey <- optaesEncryptedKey
          } yield EncryptedAesKey(keyHash, aesEncryptedKey)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, EncryptedAesKey] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optkeyHash = Some(in.readInt64())))
            }
            case 18 => {
              doParse(partialMessage.copy(optaesEncryptedKey = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSendEncryptedMessage(peer: Refs.OutPeer, rid: Long, encryptedMessage: Array[Byte], keys: Vector[Refs.EncryptedAesKey], ownKeys: Vector[Refs.EncryptedAesKey]) extends RpcRequest
    object RequestSendEncryptedMessage {
      val header = 14
      val Response = Refs.ResponseSeqDate
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer], optrid: Option[Long], optencryptedMessage: Option[Array[Byte]], eitherskeys: Vector[Either[Refs.EncryptedAesKey.Partial, Refs.EncryptedAesKey]], eithersownKeys: Vector[Either[Refs.EncryptedAesKey.Partial, Refs.EncryptedAesKey]]) {
        def toComplete: Option[RequestSendEncryptedMessage] = {
          for {
            peer <- eitherpeer.right.toOption
            rid <- optrid
            encryptedMessage <- optencryptedMessage
            keys <- {
              val eitherMsgsView = eitherskeys.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            ownKeys <- {
              val eitherMsgsView = eithersownKeys.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
          } yield RequestSendEncryptedMessage(peer, rid, encryptedMessage, keys, ownKeys)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty), None, None, Vector.empty, Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSendEncryptedMessage] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 34 => {
              doParse(partialMessage.copy(optencryptedMessage = Some(in.readBytes().toByteArray())))
            }
            case 42 => {
              doParse(partialMessage.copy(eitherskeys = partialMessage.eitherskeys :+ {
                Refs.EncryptedAesKey.parseFrom(in)
              }))
            }
            case 50 => {
              doParse(partialMessage.copy(eithersownKeys = partialMessage.eithersownKeys :+ {
                Refs.EncryptedAesKey.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSendMessage(peer: Refs.OutPeer, rid: Long, message: Refs.MessageContent) extends RpcRequest
    object RequestSendMessage {
      val header = 92
      val Response = Refs.ResponseSeqDate
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer], optrid: Option[Long], eithermessage: Either[Refs.MessageContent.Partial, Refs.MessageContent]) {
        def toComplete: Option[RequestSendMessage] = {
          for {
            peer <- eitherpeer.right.toOption
            rid <- optrid
            message <- eithermessage.right.toOption
          } yield RequestSendMessage(peer, rid, message)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty), None, Left(Refs.MessageContent.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSendMessage] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 34 => {
              doParse(partialMessage.copy(eithermessage = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.MessageContent.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestEncryptedReceived(peer: Refs.OutPeer, rid: Long) extends RpcRequest
    object RequestEncryptedReceived {
      val header = 116
      val Response = Refs.ResponseVoid
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer], optrid: Option[Long]) {
        def toComplete: Option[RequestEncryptedReceived] = {
          for {
            peer <- eitherpeer.right.toOption
            rid <- optrid
          } yield RequestEncryptedReceived(peer, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestEncryptedReceived] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestEncryptedRead(peer: Refs.OutPeer, rid: Long) extends RpcRequest
    object RequestEncryptedRead {
      val header = 117
      val Response = Refs.ResponseVoid
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer], optrid: Option[Long]) {
        def toComplete: Option[RequestEncryptedRead] = {
          for {
            peer <- eitherpeer.right.toOption
            rid <- optrid
          } yield RequestEncryptedRead(peer, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestEncryptedRead] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestMessageReceived(peer: Refs.OutPeer, date: Long) extends RpcRequest
    object RequestMessageReceived {
      val header = 55
      val Response = Refs.ResponseVoid
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer], optdate: Option[Long]) {
        def toComplete: Option[RequestMessageReceived] = {
          for {
            peer <- eitherpeer.right.toOption
            date <- optdate
          } yield RequestMessageReceived(peer, date)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestMessageReceived] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestMessageRead(peer: Refs.OutPeer, date: Long) extends RpcRequest
    object RequestMessageRead {
      val header = 57
      val Response = Refs.ResponseVoid
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer], optdate: Option[Long]) {
        def toComplete: Option[RequestMessageRead] = {
          for {
            peer <- eitherpeer.right.toOption
            date <- optdate
          } yield RequestMessageRead(peer, date)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestMessageRead] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestDeleteMessage(peer: Refs.OutPeer, rids: Vector[Long]) extends RpcRequest
    object RequestDeleteMessage {
      val header = 98
      val Response = Refs.ResponseVoid
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer], rids: Vector[Long]) {
        def toComplete: Option[RequestDeleteMessage] = {
          for (peer <- eitherpeer.right.toOption)
            yield RequestDeleteMessage(peer, rids)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty), Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestDeleteMessage] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(rids = partialMessage.rids :+ in.readInt64()))
            }
            case 26 => {
              doParse(partialMessage.copy(rids = partialMessage.rids ++ {
                val length = in.readRawVarint32()
                val limit = in.pushLimit(length)
                val values = Iterator.continually(in.readInt64()).takeWhile(_ => in.getBytesUntilLimit() > 0).toVector
                in.popLimit(limit)
                values
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestClearChat(peer: Refs.OutPeer) extends RpcRequest
    object RequestClearChat {
      val header = 99
      val Response = Refs.ResponseSeq
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer]) {
        def toComplete: Option[RequestClearChat] = {
          for (peer <- eitherpeer.right.toOption)
            yield RequestClearChat(peer)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestClearChat] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestDeleteChat(peer: Refs.OutPeer) extends RpcRequest
    object RequestDeleteChat {
      val header = 100
      val Response = Refs.ResponseSeq
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer]) {
        def toComplete: Option[RequestDeleteChat] = {
          for (peer <- eitherpeer.right.toOption)
            yield RequestDeleteChat(peer)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestDeleteChat] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateEncryptedMessage(peer: Refs.Peer, senderUid: Int, keyHash: Long, aesEncryptedKey: Array[Byte], message: Array[Byte], date: Long) extends Update
    object UpdateEncryptedMessage {
      val header = 1
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optsenderUid: Option[Int], optkeyHash: Option[Long], optaesEncryptedKey: Option[Array[Byte]], optmessage: Option[Array[Byte]], optdate: Option[Long]) {
        def toComplete: Option[UpdateEncryptedMessage] = {
          for {
            peer <- eitherpeer.right.toOption
            senderUid <- optsenderUid
            keyHash <- optkeyHash
            aesEncryptedKey <- optaesEncryptedKey
            message <- optmessage
            date <- optdate
          } yield UpdateEncryptedMessage(peer, senderUid, keyHash, aesEncryptedKey, message, date)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None, None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateEncryptedMessage] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optsenderUid = Some(in.readInt32())))
            }
            case 48 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 24 => {
              doParse(partialMessage.copy(optkeyHash = Some(in.readInt64())))
            }
            case 34 => {
              doParse(partialMessage.copy(optaesEncryptedKey = Some(in.readBytes().toByteArray())))
            }
            case 42 => {
              doParse(partialMessage.copy(optmessage = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateMessage(peer: Refs.Peer, senderUid: Int, date: Long, rid: Long, message: Refs.MessageContent) extends Update
    object UpdateMessage {
      val header = 55
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optsenderUid: Option[Int], optdate: Option[Long], optrid: Option[Long], eithermessage: Either[Refs.MessageContent.Partial, Refs.MessageContent]) {
        def toComplete: Option[UpdateMessage] = {
          for {
            peer <- eitherpeer.right.toOption
            senderUid <- optsenderUid
            date <- optdate
            rid <- optrid
            message <- eithermessage.right.toOption
          } yield UpdateMessage(peer, senderUid, date, rid, message)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None, None, None, Left(Refs.MessageContent.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateMessage] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optsenderUid = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 32 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 42 => {
              doParse(partialMessage.copy(eithermessage = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.MessageContent.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateMessageSent(peer: Refs.Peer, rid: Long, date: Long) extends Update
    object UpdateMessageSent {
      val header = 4
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optrid: Option[Long], optdate: Option[Long]) {
        def toComplete: Option[UpdateMessageSent] = {
          for {
            peer <- eitherpeer.right.toOption
            rid <- optrid
            date <- optdate
          } yield UpdateMessageSent(peer, rid, date)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateMessageSent] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 24 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateEncryptedReceived(peer: Refs.Peer, rid: Long, receivedDate: Long) extends Update
    object UpdateEncryptedReceived {
      val header = 18
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optrid: Option[Long], optreceivedDate: Option[Long]) {
        def toComplete: Option[UpdateEncryptedReceived] = {
          for {
            peer <- eitherpeer.right.toOption
            rid <- optrid
            receivedDate <- optreceivedDate
          } yield UpdateEncryptedReceived(peer, rid, receivedDate)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateEncryptedReceived] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 24 => {
              doParse(partialMessage.copy(optreceivedDate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateEncryptedRead(peer: Refs.Peer, rid: Long, readDate: Long) extends Update
    object UpdateEncryptedRead {
      val header = 52
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optrid: Option[Long], optreadDate: Option[Long]) {
        def toComplete: Option[UpdateEncryptedRead] = {
          for {
            peer <- eitherpeer.right.toOption
            rid <- optrid
            readDate <- optreadDate
          } yield UpdateEncryptedRead(peer, rid, readDate)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateEncryptedRead] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 24 => {
              doParse(partialMessage.copy(optreadDate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateEncryptedReadByMe(peer: Refs.Peer, rid: Long) extends Update
    object UpdateEncryptedReadByMe {
      val header = 53
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optrid: Option[Long]) {
        def toComplete: Option[UpdateEncryptedReadByMe] = {
          for {
            peer <- eitherpeer.right.toOption
            rid <- optrid
          } yield UpdateEncryptedReadByMe(peer, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateEncryptedReadByMe] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateMessageReceived(peer: Refs.Peer, startDate: Long, receivedDate: Long) extends Update
    object UpdateMessageReceived {
      val header = 54
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optstartDate: Option[Long], optreceivedDate: Option[Long]) {
        def toComplete: Option[UpdateMessageReceived] = {
          for {
            peer <- eitherpeer.right.toOption
            startDate <- optstartDate
            receivedDate <- optreceivedDate
          } yield UpdateMessageReceived(peer, startDate, receivedDate)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateMessageReceived] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optstartDate = Some(in.readInt64())))
            }
            case 24 => {
              doParse(partialMessage.copy(optreceivedDate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateMessageRead(peer: Refs.Peer, startDate: Long, readDate: Long) extends Update
    object UpdateMessageRead {
      val header = 19
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optstartDate: Option[Long], optreadDate: Option[Long]) {
        def toComplete: Option[UpdateMessageRead] = {
          for {
            peer <- eitherpeer.right.toOption
            startDate <- optstartDate
            readDate <- optreadDate
          } yield UpdateMessageRead(peer, startDate, readDate)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateMessageRead] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optstartDate = Some(in.readInt64())))
            }
            case 24 => {
              doParse(partialMessage.copy(optreadDate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateMessageReadByMe(peer: Refs.Peer, startDate: Long) extends Update
    object UpdateMessageReadByMe {
      val header = 50
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optstartDate: Option[Long]) {
        def toComplete: Option[UpdateMessageReadByMe] = {
          for {
            peer <- eitherpeer.right.toOption
            startDate <- optstartDate
          } yield UpdateMessageReadByMe(peer, startDate)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateMessageReadByMe] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optstartDate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateMessageDelete(peer: Refs.Peer, rids: Vector[Long]) extends Update
    object UpdateMessageDelete {
      val header = 46
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], rids: Vector[Long]) {
        def toComplete: Option[UpdateMessageDelete] = {
          for (peer <- eitherpeer.right.toOption)
            yield UpdateMessageDelete(peer, rids)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateMessageDelete] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(rids = partialMessage.rids :+ in.readInt64()))
            }
            case 18 => {
              doParse(partialMessage.copy(rids = partialMessage.rids ++ {
                val length = in.readRawVarint32()
                val limit = in.pushLimit(length)
                val values = Iterator.continually(in.readInt64()).takeWhile(_ => in.getBytesUntilLimit() > 0).toVector
                in.popLimit(limit)
                values
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateChatClear(peer: Refs.Peer) extends Update
    object UpdateChatClear {
      val header = 47
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer]) {
        def toComplete: Option[UpdateChatClear] = {
          for (peer <- eitherpeer.right.toOption)
            yield UpdateChatClear(peer)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateChatClear] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateChatDelete(peer: Refs.Peer) extends Update
    object UpdateChatDelete {
      val header = 48
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer]) {
        def toComplete: Option[UpdateChatDelete] = {
          for (peer <- eitherpeer.right.toOption)
            yield UpdateChatDelete(peer)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateChatDelete] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    trait Message
    object Message {
      def parseFrom(in: com.google.protobuf.CodedInputStream, ext: Int): Either[Any, Message] = {
        ext match {
          case 1 => Refs.TextMessage.parseFrom(in)
          case 2 => Refs.ServiceMessage.parseFrom(in)
          case 3 => Refs.FileMessage.parseFrom(in)
        }
      }
    }
    trait ServiceExtension
    object ServiceExtension {
      def parseFrom(in: com.google.protobuf.CodedInputStream, ext: Int): Either[Any, ServiceExtension] = {
        ext match {
          case 1 => Refs.ServiceExUserAdded.parseFrom(in)
          case 2 => Refs.ServiceExUserKicked.parseFrom(in)
          case 3 => Refs.ServiceExUserLeft.parseFrom(in)
          case 4 => Refs.ServiceExGroupCreated.parseFrom(in)
          case 5 => Refs.ServiceExChangedTitle.parseFrom(in)
          case 6 => Refs.ServiceExChangedAvatar.parseFrom(in)
          case 7 => Refs.ServiceExEmailContactRegistered.parseFrom(in)
        }
      }
    }
    trait FileExtension
    object FileExtension {
      def parseFrom(in: com.google.protobuf.CodedInputStream, ext: Int): Either[Any, FileExtension] = {
        ext match {
          case 1 => Refs.FileExPhoto.parseFrom(in)
          case 2 => Refs.FileExVideo.parseFrom(in)
          case 3 => Refs.FileExVoice.parseFrom(in)
        }
      }
    }
  }
  package groups {
    case class Group(id: Int, accessHash: Long, title: String, avatar: Option[Refs.Avatar], isMember: Boolean, adminUid: Int, members: Vector[Refs.Member], createDate: Long)
    object Group {
      case class Partial(optid: Option[Int], optaccessHash: Option[Long], opttitle: Option[String], opteitheravatar: Option[Option[Either[Refs.Avatar.Partial, Refs.Avatar]]], optisMember: Option[Boolean], optadminUid: Option[Int], eithersmembers: Vector[Either[Refs.Member.Partial, Refs.Member]], optcreateDate: Option[Long]) {
        def toComplete: Option[Group] = {
          for {
            id <- optid
            accessHash <- optaccessHash
            title <- opttitle
            avatar <- {
              opteitheravatar match {
                case None => None
                case Some(Some(Left(_))) => None
                case Some(None) => Some(None)
                case Some(Some(Right(msg))) => Some(Some(msg))
              }
            }
            isMember <- optisMember
            adminUid <- optadminUid
            members <- {
              val eitherMsgsView = eithersmembers.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            createDate <- optcreateDate
          } yield Group(id, accessHash, title, avatar, isMember, adminUid, members, createDate)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, Some(None), None, None, Vector.empty, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, Group] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 26 => {
              doParse(partialMessage.copy(opttitle = Some(in.readString())))
            }
            case 34 => {
              doParse(partialMessage.copy(opteitheravatar = Some(Some({
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Avatar.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))))
            }
            case 48 => {
              doParse(partialMessage.copy(optisMember = Some(in.readBool())))
            }
            case 64 => {
              doParse(partialMessage.copy(optadminUid = Some(in.readInt32())))
            }
            case 74 => {
              doParse(partialMessage.copy(eithersmembers = partialMessage.eithersmembers :+ {
                Refs.Member.parseFrom(in)
              }))
            }
            case 80 => {
              doParse(partialMessage.copy(optcreateDate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class Member(uid: Int, inviterUid: Int, date: Long)
    object Member {
      case class Partial(optuid: Option[Int], optinviterUid: Option[Int], optdate: Option[Long]) {
        def toComplete: Option[Member] = {
          for {
            uid <- optuid
            inviterUid <- optinviterUid
            date <- optdate
          } yield Member(uid, inviterUid, date)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, Member] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optinviterUid = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestCreateGroup(rid: Long, title: String, users: Vector[Refs.UserOutPeer]) extends RpcRequest
    object RequestCreateGroup {
      val header = 65
      val Response = Refs.ResponseCreateGroup
      case class Partial(optrid: Option[Long], opttitle: Option[String], eithersusers: Vector[Either[Refs.UserOutPeer.Partial, Refs.UserOutPeer]]) {
        def toComplete: Option[RequestCreateGroup] = {
          for {
            rid <- optrid
            title <- opttitle
            users <- {
              val eitherMsgsView = eithersusers.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
          } yield RequestCreateGroup(rid, title, users)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestCreateGroup] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 18 => {
              doParse(partialMessage.copy(opttitle = Some(in.readString())))
            }
            case 26 => {
              doParse(partialMessage.copy(eithersusers = partialMessage.eithersusers :+ {
                Refs.UserOutPeer.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseCreateGroup(groupPeer: Refs.GroupOutPeer, seq: Int, state: Array[Byte], users: Vector[Int], date: Long) extends RpcResponse
    object ResponseCreateGroup {
      val header = 66
      case class Partial(eithergroupPeer: Either[Refs.GroupOutPeer.Partial, Refs.GroupOutPeer], optseq: Option[Int], optstate: Option[Array[Byte]], users: Vector[Int], optdate: Option[Long]) {
        def toComplete: Option[ResponseCreateGroup] = {
          for {
            groupPeer <- eithergroupPeer.right.toOption
            seq <- optseq
            state <- optstate
            date <- optdate
          } yield ResponseCreateGroup(groupPeer, seq, state, users, date)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.GroupOutPeer.Partial.empty), None, None, Vector.empty, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseCreateGroup] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithergroupPeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.GroupOutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(optseq = Some(in.readInt32())))
            }
            case 34 => {
              doParse(partialMessage.copy(optstate = Some(in.readBytes().toByteArray())))
            }
            case 40 => {
              doParse(partialMessage.copy(users = partialMessage.users :+ in.readInt32()))
            }
            case 42 => {
              doParse(partialMessage.copy(users = partialMessage.users ++ {
                val length = in.readRawVarint32()
                val limit = in.pushLimit(length)
                val values = Iterator.continually(in.readInt32()).takeWhile(_ => in.getBytesUntilLimit() > 0).toVector
                in.popLimit(limit)
                values
              }))
            }
            case 48 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestEditGroupTitle(groupPeer: Refs.GroupOutPeer, title: String, rid: Long) extends RpcRequest
    object RequestEditGroupTitle {
      val header = 85
      val Response = Refs.ResponseSeqDate
      case class Partial(eithergroupPeer: Either[Refs.GroupOutPeer.Partial, Refs.GroupOutPeer], opttitle: Option[String], optrid: Option[Long]) {
        def toComplete: Option[RequestEditGroupTitle] = {
          for {
            groupPeer <- eithergroupPeer.right.toOption
            title <- opttitle
            rid <- optrid
          } yield RequestEditGroupTitle(groupPeer, title, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.GroupOutPeer.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestEditGroupTitle] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithergroupPeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.GroupOutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 32 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 26 => {
              doParse(partialMessage.copy(opttitle = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestEditGroupAvatar(groupPeer: Refs.GroupOutPeer, fileLocation: Refs.FileLocation, rid: Long) extends RpcRequest
    object RequestEditGroupAvatar {
      val header = 86
      val Response = Refs.ResponseEditGroupAvatar
      case class Partial(eithergroupPeer: Either[Refs.GroupOutPeer.Partial, Refs.GroupOutPeer], eitherfileLocation: Either[Refs.FileLocation.Partial, Refs.FileLocation], optrid: Option[Long]) {
        def toComplete: Option[RequestEditGroupAvatar] = {
          for {
            groupPeer <- eithergroupPeer.right.toOption
            fileLocation <- eitherfileLocation.right.toOption
            rid <- optrid
          } yield RequestEditGroupAvatar(groupPeer, fileLocation, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.GroupOutPeer.Partial.empty), Left(Refs.FileLocation.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestEditGroupAvatar] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithergroupPeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.GroupOutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 32 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 26 => {
              doParse(partialMessage.copy(eitherfileLocation = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.FileLocation.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseEditGroupAvatar(avatar: Refs.Avatar, seq: Int, state: Array[Byte], date: Long) extends RpcResponse
    object ResponseEditGroupAvatar {
      val header = 115
      case class Partial(eitheravatar: Either[Refs.Avatar.Partial, Refs.Avatar], optseq: Option[Int], optstate: Option[Array[Byte]], optdate: Option[Long]) {
        def toComplete: Option[ResponseEditGroupAvatar] = {
          for {
            avatar <- eitheravatar.right.toOption
            seq <- optseq
            state <- optstate
            date <- optdate
          } yield ResponseEditGroupAvatar(avatar, seq, state, date)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Avatar.Partial.empty), None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseEditGroupAvatar] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitheravatar = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Avatar.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optseq = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(optstate = Some(in.readBytes().toByteArray())))
            }
            case 32 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestRemoveGroupAvatar(groupPeer: Refs.GroupOutPeer, rid: Long) extends RpcRequest
    object RequestRemoveGroupAvatar {
      val header = 101
      val Response = Refs.ResponseSeqDate
      case class Partial(eithergroupPeer: Either[Refs.GroupOutPeer.Partial, Refs.GroupOutPeer], optrid: Option[Long]) {
        def toComplete: Option[RequestRemoveGroupAvatar] = {
          for {
            groupPeer <- eithergroupPeer.right.toOption
            rid <- optrid
          } yield RequestRemoveGroupAvatar(groupPeer, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.GroupOutPeer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestRemoveGroupAvatar] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithergroupPeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.GroupOutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 32 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestInviteUser(groupPeer: Refs.GroupOutPeer, user: Refs.UserOutPeer, rid: Long) extends RpcRequest
    object RequestInviteUser {
      val header = 69
      val Response = Refs.ResponseSeqDate
      case class Partial(eithergroupPeer: Either[Refs.GroupOutPeer.Partial, Refs.GroupOutPeer], eitheruser: Either[Refs.UserOutPeer.Partial, Refs.UserOutPeer], optrid: Option[Long]) {
        def toComplete: Option[RequestInviteUser] = {
          for {
            groupPeer <- eithergroupPeer.right.toOption
            user <- eitheruser.right.toOption
            rid <- optrid
          } yield RequestInviteUser(groupPeer, user, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.GroupOutPeer.Partial.empty), Left(Refs.UserOutPeer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestInviteUser] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithergroupPeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.GroupOutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 32 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 26 => {
              doParse(partialMessage.copy(eitheruser = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.UserOutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestLeaveGroup(groupPeer: Refs.GroupOutPeer, rid: Long) extends RpcRequest
    object RequestLeaveGroup {
      val header = 70
      val Response = Refs.ResponseSeqDate
      case class Partial(eithergroupPeer: Either[Refs.GroupOutPeer.Partial, Refs.GroupOutPeer], optrid: Option[Long]) {
        def toComplete: Option[RequestLeaveGroup] = {
          for {
            groupPeer <- eithergroupPeer.right.toOption
            rid <- optrid
          } yield RequestLeaveGroup(groupPeer, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.GroupOutPeer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestLeaveGroup] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithergroupPeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.GroupOutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestKickUser(groupPeer: Refs.GroupOutPeer, user: Refs.UserOutPeer, rid: Long) extends RpcRequest
    object RequestKickUser {
      val header = 71
      val Response = Refs.ResponseSeqDate
      case class Partial(eithergroupPeer: Either[Refs.GroupOutPeer.Partial, Refs.GroupOutPeer], eitheruser: Either[Refs.UserOutPeer.Partial, Refs.UserOutPeer], optrid: Option[Long]) {
        def toComplete: Option[RequestKickUser] = {
          for {
            groupPeer <- eithergroupPeer.right.toOption
            user <- eitheruser.right.toOption
            rid <- optrid
          } yield RequestKickUser(groupPeer, user, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.GroupOutPeer.Partial.empty), Left(Refs.UserOutPeer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestKickUser] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithergroupPeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.GroupOutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 32 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 26 => {
              doParse(partialMessage.copy(eitheruser = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.UserOutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateGroupInvite(groupId: Int, inviteUid: Int, date: Long, rid: Long) extends Update
    object UpdateGroupInvite {
      val header = 36
      case class Partial(optgroupId: Option[Int], optinviteUid: Option[Int], optdate: Option[Long], optrid: Option[Long]) {
        def toComplete: Option[UpdateGroupInvite] = {
          for {
            groupId <- optgroupId
            inviteUid <- optinviteUid
            date <- optdate
            rid <- optrid
          } yield UpdateGroupInvite(groupId, inviteUid, date, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateGroupInvite] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optgroupId = Some(in.readInt32())))
            }
            case 72 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 40 => {
              doParse(partialMessage.copy(optinviteUid = Some(in.readInt32())))
            }
            case 64 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateGroupUserAdded(groupId: Int, uid: Int, inviterUid: Int, date: Long, rid: Long) extends Update
    object UpdateGroupUserAdded {
      val header = 21
      case class Partial(optgroupId: Option[Int], optuid: Option[Int], optinviterUid: Option[Int], optdate: Option[Long], optrid: Option[Long]) {
        def toComplete: Option[UpdateGroupUserAdded] = {
          for {
            groupId <- optgroupId
            uid <- optuid
            inviterUid <- optinviterUid
            date <- optdate
            rid <- optrid
          } yield UpdateGroupUserAdded(groupId, uid, inviterUid, date, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateGroupUserAdded] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optgroupId = Some(in.readInt32())))
            }
            case 40 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 16 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optinviterUid = Some(in.readInt32())))
            }
            case 32 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateGroupUserLeave(groupId: Int, uid: Int, date: Long, rid: Long) extends Update
    object UpdateGroupUserLeave {
      val header = 23
      case class Partial(optgroupId: Option[Int], optuid: Option[Int], optdate: Option[Long], optrid: Option[Long]) {
        def toComplete: Option[UpdateGroupUserLeave] = {
          for {
            groupId <- optgroupId
            uid <- optuid
            date <- optdate
            rid <- optrid
          } yield UpdateGroupUserLeave(groupId, uid, date, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateGroupUserLeave] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optgroupId = Some(in.readInt32())))
            }
            case 32 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 16 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateGroupUserKick(groupId: Int, uid: Int, kickerUid: Int, date: Long, rid: Long) extends Update
    object UpdateGroupUserKick {
      val header = 24
      case class Partial(optgroupId: Option[Int], optuid: Option[Int], optkickerUid: Option[Int], optdate: Option[Long], optrid: Option[Long]) {
        def toComplete: Option[UpdateGroupUserKick] = {
          for {
            groupId <- optgroupId
            uid <- optuid
            kickerUid <- optkickerUid
            date <- optdate
            rid <- optrid
          } yield UpdateGroupUserKick(groupId, uid, kickerUid, date, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateGroupUserKick] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optgroupId = Some(in.readInt32())))
            }
            case 40 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 16 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optkickerUid = Some(in.readInt32())))
            }
            case 32 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateGroupMembersUpdate(groupId: Int, members: Vector[Refs.Member]) extends Update
    object UpdateGroupMembersUpdate {
      val header = 44
      case class Partial(optgroupId: Option[Int], eithersmembers: Vector[Either[Refs.Member.Partial, Refs.Member]]) {
        def toComplete: Option[UpdateGroupMembersUpdate] = {
          for {
            groupId <- optgroupId
            members <- {
              val eitherMsgsView = eithersmembers.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
          } yield UpdateGroupMembersUpdate(groupId, members)
        }
      }
      object Partial {
        val empty = Partial.apply(None, Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateGroupMembersUpdate] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optgroupId = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(eithersmembers = partialMessage.eithersmembers :+ {
                Refs.Member.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateGroupTitleChanged(groupId: Int, uid: Int, title: String, date: Long, rid: Long) extends Update
    object UpdateGroupTitleChanged {
      val header = 38
      case class Partial(optgroupId: Option[Int], optuid: Option[Int], opttitle: Option[String], optdate: Option[Long], optrid: Option[Long]) {
        def toComplete: Option[UpdateGroupTitleChanged] = {
          for {
            groupId <- optgroupId
            uid <- optuid
            title <- opttitle
            date <- optdate
            rid <- optrid
          } yield UpdateGroupTitleChanged(groupId, uid, title, date, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateGroupTitleChanged] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optgroupId = Some(in.readInt32())))
            }
            case 40 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 16 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(opttitle = Some(in.readString())))
            }
            case 32 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateGroupAvatarChanged(groupId: Int, uid: Int, avatar: Option[Refs.Avatar], date: Long, rid: Long) extends Update
    object UpdateGroupAvatarChanged {
      val header = 39
      case class Partial(optgroupId: Option[Int], optuid: Option[Int], opteitheravatar: Option[Option[Either[Refs.Avatar.Partial, Refs.Avatar]]], optdate: Option[Long], optrid: Option[Long]) {
        def toComplete: Option[UpdateGroupAvatarChanged] = {
          for {
            groupId <- optgroupId
            uid <- optuid
            avatar <- {
              opteitheravatar match {
                case None => None
                case Some(Some(Left(_))) => None
                case Some(None) => Some(None)
                case Some(Some(Right(msg))) => Some(Some(msg))
              }
            }
            date <- optdate
            rid <- optrid
          } yield UpdateGroupAvatarChanged(groupId, uid, avatar, date, rid)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, Some(None), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateGroupAvatarChanged] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optgroupId = Some(in.readInt32())))
            }
            case 40 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 16 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(opteitheravatar = Some(Some({
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Avatar.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))))
            }
            case 32 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  package conversations {
    trait MessageState
    object MessageState extends Enumeration with MessageState {
      type MessageState = Value
      val Sent: MessageState = Value(1)
      val Received: MessageState = Value(2)
      val Read: MessageState = Value(3)
    }
    case class HistoryMessage(senderUid: Int, rid: Long, date: Long, message: Refs.MessageContent, state: Option[Refs.MessageState])
    object HistoryMessage {
      case class Partial(optsenderUid: Option[Int], optrid: Option[Long], optdate: Option[Long], eithermessage: Either[Refs.MessageContent.Partial, Refs.MessageContent], optstate: Option[Option[Refs.MessageState]]) {
        def toComplete: Option[HistoryMessage] = {
          for {
            senderUid <- optsenderUid
            rid <- optrid
            date <- optdate
            message <- eithermessage.right.toOption
            state <- optstate
          } yield HistoryMessage(senderUid, rid, date, message, state)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None, Left(Refs.MessageContent.Partial.empty), Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, HistoryMessage] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optsenderUid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 24 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 42 => {
              doParse(partialMessage.copy(eithermessage = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.MessageContent.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 48 => {
              doParse(partialMessage.copy(optstate = Some(Some({
                Refs.MessageState(in.readEnum())
              }))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestLoadHistory(peer: Refs.OutPeer, startDate: Long, limit: Int) extends RpcRequest
    object RequestLoadHistory {
      val header = 118
      val Response = Refs.ResponseLoadHistory
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer], optstartDate: Option[Long], optlimit: Option[Int]) {
        def toComplete: Option[RequestLoadHistory] = {
          for {
            peer <- eitherpeer.right.toOption
            startDate <- optstartDate
            limit <- optlimit
          } yield RequestLoadHistory(peer, startDate, limit)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestLoadHistory] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(optstartDate = Some(in.readInt64())))
            }
            case 32 => {
              doParse(partialMessage.copy(optlimit = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseLoadHistory(history: Vector[Refs.HistoryMessage], users: Vector[Refs.User]) extends RpcResponse
    object ResponseLoadHistory {
      val header = 119
      case class Partial(eithershistory: Vector[Either[Refs.HistoryMessage.Partial, Refs.HistoryMessage]], eithersusers: Vector[Either[Refs.User.Partial, Refs.User]]) {
        def toComplete: Option[ResponseLoadHistory] = {
          for {
            history <- {
              val eitherMsgsView = eithershistory.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            users <- {
              val eitherMsgsView = eithersusers.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
          } yield ResponseLoadHistory(history, users)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty, Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseLoadHistory] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithershistory = partialMessage.eithershistory :+ {
                Refs.HistoryMessage.parseFrom(in)
              }))
            }
            case 18 => {
              doParse(partialMessage.copy(eithersusers = partialMessage.eithersusers :+ {
                Refs.User.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class Dialog(peer: Refs.Peer, unreadCount: Int, sortDate: Long, senderUid: Int, rid: Long, date: Long, message: Refs.MessageContent, state: Option[Refs.MessageState])
    object Dialog {
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optunreadCount: Option[Int], optsortDate: Option[Long], optsenderUid: Option[Int], optrid: Option[Long], optdate: Option[Long], eithermessage: Either[Refs.MessageContent.Partial, Refs.MessageContent], optstate: Option[Option[Refs.MessageState]]) {
        def toComplete: Option[Dialog] = {
          for {
            peer <- eitherpeer.right.toOption
            unreadCount <- optunreadCount
            sortDate <- optsortDate
            senderUid <- optsenderUid
            rid <- optrid
            date <- optdate
            message <- eithermessage.right.toOption
            state <- optstate
          } yield Dialog(peer, unreadCount, sortDate, senderUid, rid, date, message, state)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None, None, None, None, None, Left(Refs.MessageContent.Partial.empty), Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, Dialog] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(optunreadCount = Some(in.readInt32())))
            }
            case 32 => {
              doParse(partialMessage.copy(optsortDate = Some(in.readInt64())))
            }
            case 40 => {
              doParse(partialMessage.copy(optsenderUid = Some(in.readInt32())))
            }
            case 48 => {
              doParse(partialMessage.copy(optrid = Some(in.readInt64())))
            }
            case 56 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 66 => {
              doParse(partialMessage.copy(eithermessage = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.MessageContent.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 72 => {
              doParse(partialMessage.copy(optstate = Some(Some({
                Refs.MessageState(in.readEnum())
              }))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestLoadDialogs(startDate: Long, limit: Int) extends RpcRequest
    object RequestLoadDialogs {
      val header = 104
      val Response = Refs.ResponseLoadDialogs
      case class Partial(optstartDate: Option[Long], optlimit: Option[Int]) {
        def toComplete: Option[RequestLoadDialogs] = {
          for {
            startDate <- optstartDate
            limit <- optlimit
          } yield RequestLoadDialogs(startDate, limit)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestLoadDialogs] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optstartDate = Some(in.readInt64())))
            }
            case 16 => {
              doParse(partialMessage.copy(optlimit = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseLoadDialogs(groups: Vector[Refs.Group], users: Vector[Refs.User], dialogs: Vector[Refs.Dialog]) extends RpcResponse
    object ResponseLoadDialogs {
      val header = 105
      case class Partial(eithersgroups: Vector[Either[Refs.Group.Partial, Refs.Group]], eithersusers: Vector[Either[Refs.User.Partial, Refs.User]], eithersdialogs: Vector[Either[Refs.Dialog.Partial, Refs.Dialog]]) {
        def toComplete: Option[ResponseLoadDialogs] = {
          for {
            groups <- {
              val eitherMsgsView = eithersgroups.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            users <- {
              val eitherMsgsView = eithersusers.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            dialogs <- {
              val eitherMsgsView = eithersdialogs.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
          } yield ResponseLoadDialogs(groups, users, dialogs)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty, Vector.empty, Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseLoadDialogs] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersgroups = partialMessage.eithersgroups :+ {
                Refs.Group.parseFrom(in)
              }))
            }
            case 18 => {
              doParse(partialMessage.copy(eithersusers = partialMessage.eithersusers :+ {
                Refs.User.parseFrom(in)
              }))
            }
            case 26 => {
              doParse(partialMessage.copy(eithersdialogs = partialMessage.eithersdialogs :+ {
                Refs.Dialog.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  package encryption {
    case class UserKey(uid: Int, keyHash: Long)
    object UserKey {
      case class Partial(optuid: Option[Int], optkeyHash: Option[Long]) {
        def toComplete: Option[UserKey] = {
          for {
            uid <- optuid
            keyHash <- optkeyHash
          } yield UserKey(uid, keyHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UserKey] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optkeyHash = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class PublicKey(uid: Int, keyHash: Long, key: Array[Byte])
    object PublicKey {
      case class Partial(optuid: Option[Int], optkeyHash: Option[Long], optkey: Option[Array[Byte]]) {
        def toComplete: Option[PublicKey] = {
          for {
            uid <- optuid
            keyHash <- optkeyHash
            key <- optkey
          } yield PublicKey(uid, keyHash, key)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, PublicKey] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optkeyHash = Some(in.readInt64())))
            }
            case 26 => {
              doParse(partialMessage.copy(optkey = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateNewDevice(uid: Int, keyHash: Long, key: Option[Array[Byte]], date: Long) extends Update
    object UpdateNewDevice {
      val header = 2
      case class Partial(optuid: Option[Int], optkeyHash: Option[Long], optkey: Option[Option[Array[Byte]]], optdate: Option[Long]) {
        def toComplete: Option[UpdateNewDevice] = {
          for {
            uid <- optuid
            keyHash <- optkeyHash
            key <- optkey
            date <- optdate
          } yield UpdateNewDevice(uid, keyHash, key, date)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, Some(None), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateNewDevice] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optkeyHash = Some(in.readInt64())))
            }
            case 26 => {
              doParse(partialMessage.copy(optkey = Some(Some(in.readBytes().toByteArray()))))
            }
            case 32 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateRemovedDevice(uid: Int, keyHash: Long) extends Update
    object UpdateRemovedDevice {
      val header = 37
      case class Partial(optuid: Option[Int], optkeyHash: Option[Long]) {
        def toComplete: Option[UpdateRemovedDevice] = {
          for {
            uid <- optuid
            keyHash <- optkeyHash
          } yield UpdateRemovedDevice(uid, keyHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateRemovedDevice] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optkeyHash = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class PublicKeyRequest(uid: Int, accessHash: Long, keyHash: Long)
    object PublicKeyRequest {
      case class Partial(optuid: Option[Int], optaccessHash: Option[Long], optkeyHash: Option[Long]) {
        def toComplete: Option[PublicKeyRequest] = {
          for {
            uid <- optuid
            accessHash <- optaccessHash
            keyHash <- optkeyHash
          } yield PublicKeyRequest(uid, accessHash, keyHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, PublicKeyRequest] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 24 => {
              doParse(partialMessage.copy(optkeyHash = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestGetPublicKeys(keys: Vector[Refs.PublicKeyRequest]) extends RpcRequest
    object RequestGetPublicKeys {
      val header = 6
      val Response = Refs.ResponseGetPublicKeys
      case class Partial(eitherskeys: Vector[Either[Refs.PublicKeyRequest.Partial, Refs.PublicKeyRequest]]) {
        def toComplete: Option[RequestGetPublicKeys] = {
          for (keys <- {
            val eitherMsgsView = eitherskeys.partition(_.isLeft) match {
              case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                yield msg)
              case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                yield partialMsg)
            }
            eitherMsgsView match {
              case Right(msgs) => Some(msgs.force.toVector)
              case Left(partialMsgs) => None
            }
          })
            yield RequestGetPublicKeys(keys)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestGetPublicKeys] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherskeys = partialMessage.eitherskeys :+ {
                Refs.PublicKeyRequest.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseGetPublicKeys(keys: Vector[Refs.PublicKey]) extends RpcResponse
    object ResponseGetPublicKeys {
      val header = 24
      case class Partial(eitherskeys: Vector[Either[Refs.PublicKey.Partial, Refs.PublicKey]]) {
        def toComplete: Option[ResponseGetPublicKeys] = {
          for (keys <- {
            val eitherMsgsView = eitherskeys.partition(_.isLeft) match {
              case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                yield msg)
              case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                yield partialMsg)
            }
            eitherMsgsView match {
              case Right(msgs) => Some(msgs.force.toVector)
              case Left(partialMsgs) => None
            }
          })
            yield ResponseGetPublicKeys(keys)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseGetPublicKeys] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherskeys = partialMessage.eitherskeys :+ {
                Refs.PublicKey.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  package weak {
    case class RequestTyping(peer: Refs.OutPeer, typingType: Int) extends RpcRequest
    object RequestTyping {
      val header = 27
      val Response = Refs.ResponseVoid
      case class Partial(eitherpeer: Either[Refs.OutPeer.Partial, Refs.OutPeer], opttypingType: Option[Int]) {
        def toComplete: Option[RequestTyping] = {
          for {
            peer <- eitherpeer.right.toOption
            typingType <- opttypingType
          } yield RequestTyping(peer, typingType)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.OutPeer.Partial.empty), None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestTyping] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.OutPeer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 24 => {
              doParse(partialMessage.copy(opttypingType = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSetOnline(isOnline: Boolean, timeout: Long) extends RpcRequest
    object RequestSetOnline {
      val header = 29
      val Response = Refs.ResponseVoid
      case class Partial(optisOnline: Option[Boolean], opttimeout: Option[Long]) {
        def toComplete: Option[RequestSetOnline] = {
          for {
            isOnline <- optisOnline
            timeout <- opttimeout
          } yield RequestSetOnline(isOnline, timeout)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSetOnline] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optisOnline = Some(in.readBool())))
            }
            case 16 => {
              doParse(partialMessage.copy(opttimeout = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateTyping(peer: Refs.Peer, uid: Int, typingType: Int) extends Update
    object UpdateTyping {
      val header = 6
      case class Partial(eitherpeer: Either[Refs.Peer.Partial, Refs.Peer], optuid: Option[Int], opttypingType: Option[Int]) {
        def toComplete: Option[UpdateTyping] = {
          for {
            peer <- eitherpeer.right.toOption
            uid <- optuid
            typingType <- opttypingType
          } yield UpdateTyping(peer, uid, typingType)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Peer.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateTyping] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherpeer = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Peer.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(opttypingType = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserOnline(uid: Int) extends Update
    object UpdateUserOnline {
      val header = 7
      case class Partial(optuid: Option[Int]) {
        def toComplete: Option[UpdateUserOnline] = {
          for (uid <- optuid)
            yield UpdateUserOnline(uid)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserOnline] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserOffline(uid: Int) extends Update
    object UpdateUserOffline {
      val header = 8
      case class Partial(optuid: Option[Int]) {
        def toComplete: Option[UpdateUserOffline] = {
          for (uid <- optuid)
            yield UpdateUserOffline(uid)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserOffline] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateUserLastSeen(uid: Int, date: Long) extends Update
    object UpdateUserLastSeen {
      val header = 9
      case class Partial(optuid: Option[Int], optdate: Option[Long]) {
        def toComplete: Option[UpdateUserLastSeen] = {
          for {
            uid <- optuid
            date <- optdate
          } yield UpdateUserLastSeen(uid, date)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateUserLastSeen] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateGroupOnline(groupId: Int, count: Int) extends Update
    object UpdateGroupOnline {
      val header = 33
      case class Partial(optgroupId: Option[Int], optcount: Option[Int]) {
        def toComplete: Option[UpdateGroupOnline] = {
          for {
            groupId <- optgroupId
            count <- optcount
          } yield UpdateGroupOnline(groupId, count)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateGroupOnline] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optgroupId = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optcount = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  package files {
    case class FileLocation(fileId: Long, accessHash: Long)
    object FileLocation {
      case class Partial(optfileId: Option[Long], optaccessHash: Option[Long]) {
        def toComplete: Option[FileLocation] = {
          for {
            fileId <- optfileId
            accessHash <- optaccessHash
          } yield FileLocation(fileId, accessHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, FileLocation] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optfileId = Some(in.readInt64())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class AvatarImage(fileLocation: Refs.FileLocation, width: Int, height: Int, fileSize: Int)
    object AvatarImage {
      case class Partial(eitherfileLocation: Either[Refs.FileLocation.Partial, Refs.FileLocation], optwidth: Option[Int], optheight: Option[Int], optfileSize: Option[Int]) {
        def toComplete: Option[AvatarImage] = {
          for {
            fileLocation <- eitherfileLocation.right.toOption
            width <- optwidth
            height <- optheight
            fileSize <- optfileSize
          } yield AvatarImage(fileLocation, width, height, fileSize)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.FileLocation.Partial.empty), None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, AvatarImage] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherfileLocation = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.FileLocation.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optwidth = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optheight = Some(in.readInt32())))
            }
            case 32 => {
              doParse(partialMessage.copy(optfileSize = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class Avatar(smallImage: Option[Refs.AvatarImage], largeImage: Option[Refs.AvatarImage], fullImage: Option[Refs.AvatarImage])
    object Avatar {
      case class Partial(opteithersmallImage: Option[Option[Either[Refs.AvatarImage.Partial, Refs.AvatarImage]]], opteitherlargeImage: Option[Option[Either[Refs.AvatarImage.Partial, Refs.AvatarImage]]], opteitherfullImage: Option[Option[Either[Refs.AvatarImage.Partial, Refs.AvatarImage]]]) {
        def toComplete: Option[Avatar] = {
          for {
            smallImage <- {
              opteithersmallImage match {
                case None => None
                case Some(Some(Left(_))) => None
                case Some(None) => Some(None)
                case Some(Some(Right(msg))) => Some(Some(msg))
              }
            }
            largeImage <- {
              opteitherlargeImage match {
                case None => None
                case Some(Some(Left(_))) => None
                case Some(None) => Some(None)
                case Some(Some(Right(msg))) => Some(Some(msg))
              }
            }
            fullImage <- {
              opteitherfullImage match {
                case None => None
                case Some(Some(Left(_))) => None
                case Some(None) => Some(None)
                case Some(Some(Right(msg))) => Some(Some(msg))
              }
            }
          } yield Avatar(smallImage, largeImage, fullImage)
        }
      }
      object Partial {
        val empty = Partial.apply(Some(None), Some(None), Some(None))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, Avatar] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(opteithersmallImage = Some(Some({
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.AvatarImage.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))))
            }
            case 18 => {
              doParse(partialMessage.copy(opteitherlargeImage = Some(Some({
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.AvatarImage.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))))
            }
            case 26 => {
              doParse(partialMessage.copy(opteitherfullImage = Some(Some({
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.AvatarImage.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class FastThumb(w: Int, h: Int, thumb: Array[Byte])
    object FastThumb {
      case class Partial(optw: Option[Int], opth: Option[Int], optthumb: Option[Array[Byte]]) {
        def toComplete: Option[FastThumb] = {
          for {
            w <- optw
            h <- opth
            thumb <- optthumb
          } yield FastThumb(w, h, thumb)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, FastThumb] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optw = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(opth = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(optthumb = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestGetFile(fileLocation: Refs.FileLocation, offset: Int, limit: Int) extends RpcRequest
    object RequestGetFile {
      val header = 16
      val Response = Refs.ResponseGetFile
      case class Partial(eitherfileLocation: Either[Refs.FileLocation.Partial, Refs.FileLocation], optoffset: Option[Int], optlimit: Option[Int]) {
        def toComplete: Option[RequestGetFile] = {
          for {
            fileLocation <- eitherfileLocation.right.toOption
            offset <- optoffset
            limit <- optlimit
          } yield RequestGetFile(fileLocation, offset, limit)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.FileLocation.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestGetFile] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherfileLocation = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.FileLocation.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optoffset = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optlimit = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseGetFile(payload: Array[Byte]) extends RpcResponse
    object ResponseGetFile {
      val header = 17
      case class Partial(optpayload: Option[Array[Byte]]) {
        def toComplete: Option[ResponseGetFile] = {
          for (payload <- optpayload)
            yield ResponseGetFile(payload)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseGetFile] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(optpayload = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UploadConfig(serverData: Array[Byte])
    object UploadConfig {
      case class Partial(optserverData: Option[Array[Byte]]) {
        def toComplete: Option[UploadConfig] = {
          for (serverData <- optserverData)
            yield UploadConfig(serverData)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UploadConfig] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(optserverData = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    trait RequestStartUpload extends RpcRequest
    case object RequestStartUpload extends RequestStartUpload {
      val header = 18
      val Response = Refs.ResponseStartUpload
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Unit, RequestStartUpload] = {
        def doParse: Unit = {
          in.readTag() match {
            case 0 => {
              ()
            }
            case default => if (in.skipField(default) == true) doParse
            else {
              ()
            }
          }
        }
        doParse
        Right(RequestStartUpload)
      }
    }
    case class ResponseStartUpload(config: Refs.UploadConfig) extends RpcResponse
    object ResponseStartUpload {
      val header = 19
      case class Partial(eitherconfig: Either[Refs.UploadConfig.Partial, Refs.UploadConfig]) {
        def toComplete: Option[ResponseStartUpload] = {
          for (config <- eitherconfig.right.toOption)
            yield ResponseStartUpload(config)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.UploadConfig.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseStartUpload] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherconfig = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.UploadConfig.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestUploadPart(config: Refs.UploadConfig, blockIndex: Int, payload: Array[Byte]) extends RpcRequest
    object RequestUploadPart {
      val header = 20
      val Response = Refs.ResponseVoid
      case class Partial(eitherconfig: Either[Refs.UploadConfig.Partial, Refs.UploadConfig], optblockIndex: Option[Int], optpayload: Option[Array[Byte]]) {
        def toComplete: Option[RequestUploadPart] = {
          for {
            config <- eitherconfig.right.toOption
            blockIndex <- optblockIndex
            payload <- optpayload
          } yield RequestUploadPart(config, blockIndex, payload)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.UploadConfig.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestUploadPart] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherconfig = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.UploadConfig.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optblockIndex = Some(in.readInt32())))
            }
            case 26 => {
              doParse(partialMessage.copy(optpayload = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestCompleteUpload(config: Refs.UploadConfig, blocksCount: Int, crc32: Long) extends RpcRequest
    object RequestCompleteUpload {
      val header = 22
      val Response = Refs.ResponseCompleteUpload
      case class Partial(eitherconfig: Either[Refs.UploadConfig.Partial, Refs.UploadConfig], optblocksCount: Option[Int], optcrc32: Option[Long]) {
        def toComplete: Option[RequestCompleteUpload] = {
          for {
            config <- eitherconfig.right.toOption
            blocksCount <- optblocksCount
            crc32 <- optcrc32
          } yield RequestCompleteUpload(config, blocksCount, crc32)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.UploadConfig.Partial.empty), None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestCompleteUpload] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherconfig = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.UploadConfig.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 16 => {
              doParse(partialMessage.copy(optblocksCount = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optcrc32 = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseCompleteUpload(location: Refs.FileLocation) extends RpcResponse
    object ResponseCompleteUpload {
      val header = 23
      case class Partial(eitherlocation: Either[Refs.FileLocation.Partial, Refs.FileLocation]) {
        def toComplete: Option[ResponseCompleteUpload] = {
          for (location <- eitherlocation.right.toOption)
            yield ResponseCompleteUpload(location)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.FileLocation.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseCompleteUpload] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherlocation = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.FileLocation.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  package push {
    case class RequestRegisterGooglePush(projectId: Long, token: String) extends RpcRequest
    object RequestRegisterGooglePush {
      val header = 51
      val Response = Refs.ResponseVoid
      case class Partial(optprojectId: Option[Long], opttoken: Option[String]) {
        def toComplete: Option[RequestRegisterGooglePush] = {
          for {
            projectId <- optprojectId
            token <- opttoken
          } yield RequestRegisterGooglePush(projectId, token)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestRegisterGooglePush] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optprojectId = Some(in.readInt64())))
            }
            case 18 => {
              doParse(partialMessage.copy(opttoken = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestRegisterApplePush(apnsKey: Int, token: String) extends RpcRequest
    object RequestRegisterApplePush {
      val header = 76
      val Response = Refs.ResponseVoid
      case class Partial(optapnsKey: Option[Int], opttoken: Option[String]) {
        def toComplete: Option[RequestRegisterApplePush] = {
          for {
            apnsKey <- optapnsKey
            token <- opttoken
          } yield RequestRegisterApplePush(apnsKey, token)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestRegisterApplePush] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optapnsKey = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(opttoken = Some(in.readString())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    trait RequestUnregisterPush extends RpcRequest
    case object RequestUnregisterPush extends RequestUnregisterPush {
      val header = 52
      val Response = Refs.ResponseVoid
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Unit, RequestUnregisterPush] = {
        def doParse: Unit = {
          in.readTag() match {
            case 0 => {
              ()
            }
            case default => if (in.skipField(default) == true) doParse
            else {
              ()
            }
          }
        }
        doParse
        Right(RequestUnregisterPush)
      }
    }
  }
  package peers {
    trait PeerType
    object PeerType extends Enumeration with PeerType {
      type PeerType = Value
      val Private: PeerType = Value(1)
      val Group: PeerType = Value(2)
      val Email: PeerType = Value(3)
    }
    case class Peer(`type`: Refs.PeerType, id: Int)
    object Peer {
      case class Partial(opttype: Option[Refs.PeerType], optid: Option[Int]) {
        def toComplete: Option[Peer] = {
          for {
            `type` <- opttype
            id <- optid
          } yield Peer(`type`, id)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, Peer] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(opttype = Some({
                Refs.PeerType(in.readEnum())
              })))
            }
            case 16 => {
              doParse(partialMessage.copy(optid = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class OutPeer(`type`: Refs.PeerType, id: Int, accessHash: Long)
    object OutPeer {
      case class Partial(opttype: Option[Refs.PeerType], optid: Option[Int], optaccessHash: Option[Long]) {
        def toComplete: Option[OutPeer] = {
          for {
            `type` <- opttype
            id <- optid
            accessHash <- optaccessHash
          } yield OutPeer(`type`, id, accessHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, OutPeer] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(opttype = Some({
                Refs.PeerType(in.readEnum())
              })))
            }
            case 16 => {
              doParse(partialMessage.copy(optid = Some(in.readInt32())))
            }
            case 24 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UserOutPeer(uid: Int, accessHash: Long)
    object UserOutPeer {
      case class Partial(optuid: Option[Int], optaccessHash: Option[Long]) {
        def toComplete: Option[UserOutPeer] = {
          for {
            uid <- optuid
            accessHash <- optaccessHash
          } yield UserOutPeer(uid, accessHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UserOutPeer] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optuid = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class GroupOutPeer(groupId: Int, accessHash: Long)
    object GroupOutPeer {
      case class Partial(optgroupId: Option[Int], optaccessHash: Option[Long]) {
        def toComplete: Option[GroupOutPeer] = {
          for {
            groupId <- optgroupId
            accessHash <- optaccessHash
          } yield GroupOutPeer(groupId, accessHash)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, GroupOutPeer] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optgroupId = Some(in.readInt32())))
            }
            case 16 => {
              doParse(partialMessage.copy(optaccessHash = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  package sequence {
    case class SeqUpdate(seq: Int, state: Array[Byte], updateHeader: Int, update: Array[Byte]) extends UpdateBox
    case class FatSeqUpdate(seq: Int, state: Array[Byte], updateHeader: Int, update: Array[Byte], users: Vector[Refs.User], groups: Vector[Refs.Group], phones: Vector[Refs.Phone], emails: Vector[Refs.Email]) extends UpdateBox
    case class WeakUpdate(date: Long, updateHeader: Int, update: Array[Byte]) extends UpdateBox
    case object SeqUpdateTooLong extends UpdateBox
    trait RequestGetState extends RpcRequest
    case object RequestGetState extends RequestGetState {
      val header = 9
      val Response = Refs.ResponseSeq
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Unit, RequestGetState] = {
        def doParse: Unit = {
          in.readTag() match {
            case 0 => {
              ()
            }
            case default => if (in.skipField(default) == true) doParse
            else {
              ()
            }
          }
        }
        doParse
        Right(RequestGetState)
      }
    }
    case class DifferenceUpdate(updateHeader: Int, update: Array[Byte])
    object DifferenceUpdate {
      case class Partial(optupdateHeader: Option[Int], optupdate: Option[Array[Byte]]) {
        def toComplete: Option[DifferenceUpdate] = {
          for {
            updateHeader <- optupdateHeader
            update <- optupdate
          } yield DifferenceUpdate(updateHeader, update)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, DifferenceUpdate] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optupdateHeader = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(optupdate = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestGetDifference(seq: Int, state: Array[Byte]) extends RpcRequest
    object RequestGetDifference {
      val header = 11
      val Response = Refs.ResponseGetDifference
      case class Partial(optseq: Option[Int], optstate: Option[Array[Byte]]) {
        def toComplete: Option[RequestGetDifference] = {
          for {
            seq <- optseq
            state <- optstate
          } yield RequestGetDifference(seq, state)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestGetDifference] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optseq = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(optstate = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseGetDifference(seq: Int, state: Array[Byte], users: Vector[Refs.User], updates: Vector[Refs.DifferenceUpdate], needMore: Boolean, groups: Vector[Refs.Group], phones: Vector[Refs.Phone], emails: Vector[Refs.Email]) extends RpcResponse
    object ResponseGetDifference {
      val header = 12
      case class Partial(optseq: Option[Int], optstate: Option[Array[Byte]], eithersusers: Vector[Either[Refs.User.Partial, Refs.User]], eithersupdates: Vector[Either[Refs.DifferenceUpdate.Partial, Refs.DifferenceUpdate]], optneedMore: Option[Boolean], eithersgroups: Vector[Either[Refs.Group.Partial, Refs.Group]], eithersphones: Vector[Either[Refs.Phone.Partial, Refs.Phone]], eithersemails: Vector[Either[Refs.Email.Partial, Refs.Email]]) {
        def toComplete: Option[ResponseGetDifference] = {
          for {
            seq <- optseq
            state <- optstate
            users <- {
              val eitherMsgsView = eithersusers.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            updates <- {
              val eitherMsgsView = eithersupdates.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            needMore <- optneedMore
            groups <- {
              val eitherMsgsView = eithersgroups.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            phones <- {
              val eitherMsgsView = eithersphones.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
            emails <- {
              val eitherMsgsView = eithersemails.partition(_.isLeft) match {
                case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                  yield msg)
                case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                  yield partialMsg)
              }
              eitherMsgsView match {
                case Right(msgs) => Some(msgs.force.toVector)
                case Left(partialMsgs) => None
              }
            }
          } yield ResponseGetDifference(seq, state, users, updates, needMore, groups, phones, emails)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, Vector.empty, Vector.empty, None, Vector.empty, Vector.empty, Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseGetDifference] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optseq = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(optstate = Some(in.readBytes().toByteArray())))
            }
            case 26 => {
              doParse(partialMessage.copy(eithersusers = partialMessage.eithersusers :+ {
                Refs.User.parseFrom(in)
              }))
            }
            case 50 => {
              doParse(partialMessage.copy(eithersgroups = partialMessage.eithersgroups :+ {
                Refs.Group.parseFrom(in)
              }))
            }
            case 58 => {
              doParse(partialMessage.copy(eithersphones = partialMessage.eithersphones :+ {
                Refs.Phone.parseFrom(in)
              }))
            }
            case 66 => {
              doParse(partialMessage.copy(eithersemails = partialMessage.eithersemails :+ {
                Refs.Email.parseFrom(in)
              }))
            }
            case 34 => {
              doParse(partialMessage.copy(eithersupdates = partialMessage.eithersupdates :+ {
                Refs.DifferenceUpdate.parseFrom(in)
              }))
            }
            case 40 => {
              doParse(partialMessage.copy(optneedMore = Some(in.readBool())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSubscribeToOnline(users: Vector[Refs.UserOutPeer]) extends RpcRequest
    object RequestSubscribeToOnline {
      val header = 32
      val Response = Refs.ResponseVoid
      case class Partial(eithersusers: Vector[Either[Refs.UserOutPeer.Partial, Refs.UserOutPeer]]) {
        def toComplete: Option[RequestSubscribeToOnline] = {
          for (users <- {
            val eitherMsgsView = eithersusers.partition(_.isLeft) match {
              case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                yield msg)
              case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                yield partialMsg)
            }
            eitherMsgsView match {
              case Right(msgs) => Some(msgs.force.toVector)
              case Left(partialMsgs) => None
            }
          })
            yield RequestSubscribeToOnline(users)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSubscribeToOnline] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersusers = partialMessage.eithersusers :+ {
                Refs.UserOutPeer.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSubscribeFromOnline(users: Vector[Refs.UserOutPeer]) extends RpcRequest
    object RequestSubscribeFromOnline {
      val header = 33
      val Response = Refs.ResponseVoid
      case class Partial(eithersusers: Vector[Either[Refs.UserOutPeer.Partial, Refs.UserOutPeer]]) {
        def toComplete: Option[RequestSubscribeFromOnline] = {
          for (users <- {
            val eitherMsgsView = eithersusers.partition(_.isLeft) match {
              case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                yield msg)
              case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                yield partialMsg)
            }
            eitherMsgsView match {
              case Right(msgs) => Some(msgs.force.toVector)
              case Left(partialMsgs) => None
            }
          })
            yield RequestSubscribeFromOnline(users)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSubscribeFromOnline] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersusers = partialMessage.eithersusers :+ {
                Refs.UserOutPeer.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSubscribeToGroupOnline(groups: Vector[Refs.GroupOutPeer]) extends RpcRequest
    object RequestSubscribeToGroupOnline {
      val header = 74
      val Response = Refs.ResponseVoid
      case class Partial(eithersgroups: Vector[Either[Refs.GroupOutPeer.Partial, Refs.GroupOutPeer]]) {
        def toComplete: Option[RequestSubscribeToGroupOnline] = {
          for (groups <- {
            val eitherMsgsView = eithersgroups.partition(_.isLeft) match {
              case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                yield msg)
              case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                yield partialMsg)
            }
            eitherMsgsView match {
              case Right(msgs) => Some(msgs.force.toVector)
              case Left(partialMsgs) => None
            }
          })
            yield RequestSubscribeToGroupOnline(groups)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSubscribeToGroupOnline] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersgroups = partialMessage.eithersgroups :+ {
                Refs.GroupOutPeer.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class RequestSubscribeFromGroupOnline(groups: Vector[Refs.GroupOutPeer]) extends RpcRequest
    object RequestSubscribeFromGroupOnline {
      val header = 75
      val Response = Refs.ResponseVoid
      case class Partial(eithersgroups: Vector[Either[Refs.GroupOutPeer.Partial, Refs.GroupOutPeer]]) {
        def toComplete: Option[RequestSubscribeFromGroupOnline] = {
          for (groups <- {
            val eitherMsgsView = eithersgroups.partition(_.isLeft) match {
              case (Vector(), rights) => Right(for (Right(msg) <- rights.view)
                yield msg)
              case (lefts, _) => Left(for (Left(partialMsg) <- lefts.view)
                yield partialMsg)
            }
            eitherMsgsView match {
              case Right(msgs) => Some(msgs.force.toVector)
              case Left(partialMsgs) => None
            }
          })
            yield RequestSubscribeFromGroupOnline(groups)
        }
      }
      object Partial {
        val empty = Partial.apply(Vector.empty)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, RequestSubscribeFromGroupOnline] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eithersgroups = partialMessage.eithersgroups :+ {
                Refs.GroupOutPeer.parseFrom(in)
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  package misc {
    trait ResponseVoid extends RpcResponse
    case object ResponseVoid extends ResponseVoid {
      val header = 50
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Unit, ResponseVoid] = {
        def doParse: Unit = {
          in.readTag() match {
            case 0 => {
              ()
            }
            case default => if (in.skipField(default) == true) doParse
            else {
              ()
            }
          }
        }
        doParse
        Right(ResponseVoid)
      }
    }
    case class ResponseSeq(seq: Int, state: Array[Byte]) extends RpcResponse
    object ResponseSeq {
      val header = 72
      case class Partial(optseq: Option[Int], optstate: Option[Array[Byte]]) {
        def toComplete: Option[ResponseSeq] = {
          for {
            seq <- optseq
            state <- optstate
          } yield ResponseSeq(seq, state)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseSeq] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optseq = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(optstate = Some(in.readBytes().toByteArray())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class ResponseSeqDate(seq: Int, state: Array[Byte], date: Long) extends RpcResponse
    object ResponseSeqDate {
      val header = 102
      case class Partial(optseq: Option[Int], optstate: Option[Array[Byte]], optdate: Option[Long]) {
        def toComplete: Option[ResponseSeqDate] = {
          for {
            seq <- optseq
            state <- optstate
            date <- optdate
          } yield ResponseSeqDate(seq, state, date)
        }
      }
      object Partial {
        val empty = Partial.apply(None, None, None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, ResponseSeqDate] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optseq = Some(in.readInt32())))
            }
            case 18 => {
              doParse(partialMessage.copy(optstate = Some(in.readBytes().toByteArray())))
            }
            case 24 => {
              doParse(partialMessage.copy(optdate = Some(in.readInt64())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class Config(maxGroupSize: Int)
    object Config {
      case class Partial(optmaxGroupSize: Option[Int]) {
        def toComplete: Option[Config] = {
          for (maxGroupSize <- optmaxGroupSize)
            yield Config(maxGroupSize)
        }
      }
      object Partial {
        val empty = Partial.apply(None)
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, Config] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 8 => {
              doParse(partialMessage.copy(optmaxGroupSize = Some(in.readInt32())))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
    case class UpdateConfig(config: Refs.Config) extends Update
    object UpdateConfig {
      val header = 42
      case class Partial(eitherconfig: Either[Refs.Config.Partial, Refs.Config]) {
        def toComplete: Option[UpdateConfig] = {
          for (config <- eitherconfig.right.toOption)
            yield UpdateConfig(config)
        }
      }
      object Partial {
        val empty = Partial.apply(Left(Refs.Config.Partial.empty))
      }
      def parseFrom(in: com.google.protobuf.CodedInputStream): Either[Partial, UpdateConfig] = {
        def doParse(partialMessage: Partial): Partial = {
          in.readTag() match {
            case 10 => {
              doParse(partialMessage.copy(eitherconfig = {
                val length = in.readRawVarint32()
                val oldLimit = in.pushLimit(length)
                val message = Refs.Config.parseFrom(in)
                in.checkLastTagWas(0)
                in.popLimit(oldLimit)
                message
              }))
            }
            case 0 => partialMessage
            case default => if (in.skipField(default) == true) doParse(partialMessage)
            else partialMessage
          }
        }
        val partialMessage = doParse(Partial.empty)
        partialMessage.toComplete.map(Right(_)).getOrElse({
          Left(partialMessage)
        })
      }
    }
  }
  object Refs {
    type RequestSendAuthCode = auth.RequestSendAuthCode
    val RequestSendAuthCode = auth.RequestSendAuthCode
    type ResponseSendAuthCode = auth.ResponseSendAuthCode
    val ResponseSendAuthCode = auth.ResponseSendAuthCode
    type RequestSendAuthCall = auth.RequestSendAuthCall
    val RequestSendAuthCall = auth.RequestSendAuthCall
    type ResponseAuth = auth.ResponseAuth
    val ResponseAuth = auth.ResponseAuth
    type RequestSignIn = auth.RequestSignIn
    val RequestSignIn = auth.RequestSignIn
    type RequestSignUp = auth.RequestSignUp
    val RequestSignUp = auth.RequestSignUp
    type AuthSession = auth.AuthSession
    val AuthSession = auth.AuthSession
    val RequestGetAuthSessions = auth.RequestGetAuthSessions
    type ResponseGetAuthSessions = auth.ResponseGetAuthSessions
    val ResponseGetAuthSessions = auth.ResponseGetAuthSessions
    type RequestTerminateSession = auth.RequestTerminateSession
    val RequestTerminateSession = auth.RequestTerminateSession
    val RequestTerminateAllSessions = auth.RequestTerminateAllSessions
    val RequestSignOut = auth.RequestSignOut
    type Sex = users.Sex.Sex
    val Sex = users.Sex
    type UserState = users.UserState.UserState
    val UserState = users.UserState
    type Phone = users.Phone
    val Phone = users.Phone
    type Email = users.Email
    val Email = users.Email
    type User = users.User
    val User = users.User
    type RequestEditUserLocalName = users.RequestEditUserLocalName
    val RequestEditUserLocalName = users.RequestEditUserLocalName
    type UpdateUserAvatarChanged = users.UpdateUserAvatarChanged
    val UpdateUserAvatarChanged = users.UpdateUserAvatarChanged
    type UpdateUserNameChanged = users.UpdateUserNameChanged
    val UpdateUserNameChanged = users.UpdateUserNameChanged
    type UpdateUserLocalNameChanged = users.UpdateUserLocalNameChanged
    val UpdateUserLocalNameChanged = users.UpdateUserLocalNameChanged
    type UpdateUserPhoneAdded = users.UpdateUserPhoneAdded
    val UpdateUserPhoneAdded = users.UpdateUserPhoneAdded
    type UpdateUserPhoneRemoved = users.UpdateUserPhoneRemoved
    val UpdateUserPhoneRemoved = users.UpdateUserPhoneRemoved
    type UpdatePhoneTitleChanged = users.UpdatePhoneTitleChanged
    val UpdatePhoneTitleChanged = users.UpdatePhoneTitleChanged
    type UpdatePhoneMoved = users.UpdatePhoneMoved
    val UpdatePhoneMoved = users.UpdatePhoneMoved
    type UpdateUserEmailAdded = users.UpdateUserEmailAdded
    val UpdateUserEmailAdded = users.UpdateUserEmailAdded
    type UpdateUserEmailRemoved = users.UpdateUserEmailRemoved
    val UpdateUserEmailRemoved = users.UpdateUserEmailRemoved
    type UpdateEmailTitleChanged = users.UpdateEmailTitleChanged
    val UpdateEmailTitleChanged = users.UpdateEmailTitleChanged
    type UpdateEmailMoved = users.UpdateEmailMoved
    val UpdateEmailMoved = users.UpdateEmailMoved
    type UpdateUserContactsChanged = users.UpdateUserContactsChanged
    val UpdateUserContactsChanged = users.UpdateUserContactsChanged
    type UpdateUserStateChanged = users.UpdateUserStateChanged
    val UpdateUserStateChanged = users.UpdateUserStateChanged
    type RequestEditName = profile.RequestEditName
    val RequestEditName = profile.RequestEditName
    type RequestEditAvatar = profile.RequestEditAvatar
    val RequestEditAvatar = profile.RequestEditAvatar
    type ResponseEditAvatar = profile.ResponseEditAvatar
    val ResponseEditAvatar = profile.ResponseEditAvatar
    val RequestRemoveAvatar = profile.RequestRemoveAvatar
    type RequestSendEmailCode = profile.RequestSendEmailCode
    val RequestSendEmailCode = profile.RequestSendEmailCode
    type RequestDetachEmail = profile.RequestDetachEmail
    val RequestDetachEmail = profile.RequestDetachEmail
    type RequestChangePhoneTitle = profile.RequestChangePhoneTitle
    val RequestChangePhoneTitle = profile.RequestChangePhoneTitle
    type RequestChangeEmailTitle = profile.RequestChangeEmailTitle
    val RequestChangeEmailTitle = profile.RequestChangeEmailTitle
    type PhoneToImport = contacts.PhoneToImport
    val PhoneToImport = contacts.PhoneToImport
    type EmailToImport = contacts.EmailToImport
    val EmailToImport = contacts.EmailToImport
    type RequestImportContacts = contacts.RequestImportContacts
    val RequestImportContacts = contacts.RequestImportContacts
    type ResponseImportContacts = contacts.ResponseImportContacts
    val ResponseImportContacts = contacts.ResponseImportContacts
    type RequestGetContacts = contacts.RequestGetContacts
    val RequestGetContacts = contacts.RequestGetContacts
    type ResponseGetContacts = contacts.ResponseGetContacts
    val ResponseGetContacts = contacts.ResponseGetContacts
    type RequestRemoveContact = contacts.RequestRemoveContact
    val RequestRemoveContact = contacts.RequestRemoveContact
    type RequestAddContact = contacts.RequestAddContact
    val RequestAddContact = contacts.RequestAddContact
    type RequestSearchContacts = contacts.RequestSearchContacts
    val RequestSearchContacts = contacts.RequestSearchContacts
    type ResponseSearchContacts = contacts.ResponseSearchContacts
    val ResponseSearchContacts = contacts.ResponseSearchContacts
    type UpdateContactRegistered = contacts.UpdateContactRegistered
    val UpdateContactRegistered = contacts.UpdateContactRegistered
    type UpdateEmailContactRegistered = contacts.UpdateEmailContactRegistered
    val UpdateEmailContactRegistered = contacts.UpdateEmailContactRegistered
    type UpdateContactsAdded = contacts.UpdateContactsAdded
    val UpdateContactsAdded = contacts.UpdateContactsAdded
    type UpdateContactsRemoved = contacts.UpdateContactsRemoved
    val UpdateContactsRemoved = contacts.UpdateContactsRemoved
    type MessageContent = messaging.MessageContent
    val MessageContent = messaging.MessageContent
    type TextMessage = messaging.TextMessage
    val TextMessage = messaging.TextMessage
    type ServiceMessage = messaging.ServiceMessage
    val ServiceMessage = messaging.ServiceMessage
    type ServiceExUserAdded = messaging.ServiceExUserAdded
    val ServiceExUserAdded = messaging.ServiceExUserAdded
    type ServiceExUserKicked = messaging.ServiceExUserKicked
    val ServiceExUserKicked = messaging.ServiceExUserKicked
    val ServiceExUserLeft = messaging.ServiceExUserLeft
    val ServiceExGroupCreated = messaging.ServiceExGroupCreated
    type ServiceExChangedTitle = messaging.ServiceExChangedTitle
    val ServiceExChangedTitle = messaging.ServiceExChangedTitle
    type ServiceExChangedAvatar = messaging.ServiceExChangedAvatar
    val ServiceExChangedAvatar = messaging.ServiceExChangedAvatar
    type ServiceExEmailContactRegistered = messaging.ServiceExEmailContactRegistered
    val ServiceExEmailContactRegistered = messaging.ServiceExEmailContactRegistered
    type FileMessage = messaging.FileMessage
    val FileMessage = messaging.FileMessage
    type FileExPhoto = messaging.FileExPhoto
    val FileExPhoto = messaging.FileExPhoto
    type FileExVideo = messaging.FileExVideo
    val FileExVideo = messaging.FileExVideo
    type FileExVoice = messaging.FileExVoice
    val FileExVoice = messaging.FileExVoice
    type WrongKeysErrorData = messaging.WrongKeysErrorData
    val WrongKeysErrorData = messaging.WrongKeysErrorData
    type EncryptedAesKey = messaging.EncryptedAesKey
    val EncryptedAesKey = messaging.EncryptedAesKey
    type RequestSendEncryptedMessage = messaging.RequestSendEncryptedMessage
    val RequestSendEncryptedMessage = messaging.RequestSendEncryptedMessage
    type RequestSendMessage = messaging.RequestSendMessage
    val RequestSendMessage = messaging.RequestSendMessage
    type RequestEncryptedReceived = messaging.RequestEncryptedReceived
    val RequestEncryptedReceived = messaging.RequestEncryptedReceived
    type RequestEncryptedRead = messaging.RequestEncryptedRead
    val RequestEncryptedRead = messaging.RequestEncryptedRead
    type RequestMessageReceived = messaging.RequestMessageReceived
    val RequestMessageReceived = messaging.RequestMessageReceived
    type RequestMessageRead = messaging.RequestMessageRead
    val RequestMessageRead = messaging.RequestMessageRead
    type RequestDeleteMessage = messaging.RequestDeleteMessage
    val RequestDeleteMessage = messaging.RequestDeleteMessage
    type RequestClearChat = messaging.RequestClearChat
    val RequestClearChat = messaging.RequestClearChat
    type RequestDeleteChat = messaging.RequestDeleteChat
    val RequestDeleteChat = messaging.RequestDeleteChat
    type UpdateEncryptedMessage = messaging.UpdateEncryptedMessage
    val UpdateEncryptedMessage = messaging.UpdateEncryptedMessage
    type UpdateMessage = messaging.UpdateMessage
    val UpdateMessage = messaging.UpdateMessage
    type UpdateMessageSent = messaging.UpdateMessageSent
    val UpdateMessageSent = messaging.UpdateMessageSent
    type UpdateEncryptedReceived = messaging.UpdateEncryptedReceived
    val UpdateEncryptedReceived = messaging.UpdateEncryptedReceived
    type UpdateEncryptedRead = messaging.UpdateEncryptedRead
    val UpdateEncryptedRead = messaging.UpdateEncryptedRead
    type UpdateEncryptedReadByMe = messaging.UpdateEncryptedReadByMe
    val UpdateEncryptedReadByMe = messaging.UpdateEncryptedReadByMe
    type UpdateMessageReceived = messaging.UpdateMessageReceived
    val UpdateMessageReceived = messaging.UpdateMessageReceived
    type UpdateMessageRead = messaging.UpdateMessageRead
    val UpdateMessageRead = messaging.UpdateMessageRead
    type UpdateMessageReadByMe = messaging.UpdateMessageReadByMe
    val UpdateMessageReadByMe = messaging.UpdateMessageReadByMe
    type UpdateMessageDelete = messaging.UpdateMessageDelete
    val UpdateMessageDelete = messaging.UpdateMessageDelete
    type UpdateChatClear = messaging.UpdateChatClear
    val UpdateChatClear = messaging.UpdateChatClear
    type UpdateChatDelete = messaging.UpdateChatDelete
    val UpdateChatDelete = messaging.UpdateChatDelete
    type Message = messaging.Message
    val Message = messaging.Message
    type ServiceExtension = messaging.ServiceExtension
    val ServiceExtension = messaging.ServiceExtension
    type FileExtension = messaging.FileExtension
    val FileExtension = messaging.FileExtension
    type Group = groups.Group
    val Group = groups.Group
    type Member = groups.Member
    val Member = groups.Member
    type RequestCreateGroup = groups.RequestCreateGroup
    val RequestCreateGroup = groups.RequestCreateGroup
    type ResponseCreateGroup = groups.ResponseCreateGroup
    val ResponseCreateGroup = groups.ResponseCreateGroup
    type RequestEditGroupTitle = groups.RequestEditGroupTitle
    val RequestEditGroupTitle = groups.RequestEditGroupTitle
    type RequestEditGroupAvatar = groups.RequestEditGroupAvatar
    val RequestEditGroupAvatar = groups.RequestEditGroupAvatar
    type ResponseEditGroupAvatar = groups.ResponseEditGroupAvatar
    val ResponseEditGroupAvatar = groups.ResponseEditGroupAvatar
    type RequestRemoveGroupAvatar = groups.RequestRemoveGroupAvatar
    val RequestRemoveGroupAvatar = groups.RequestRemoveGroupAvatar
    type RequestInviteUser = groups.RequestInviteUser
    val RequestInviteUser = groups.RequestInviteUser
    type RequestLeaveGroup = groups.RequestLeaveGroup
    val RequestLeaveGroup = groups.RequestLeaveGroup
    type RequestKickUser = groups.RequestKickUser
    val RequestKickUser = groups.RequestKickUser
    type UpdateGroupInvite = groups.UpdateGroupInvite
    val UpdateGroupInvite = groups.UpdateGroupInvite
    type UpdateGroupUserAdded = groups.UpdateGroupUserAdded
    val UpdateGroupUserAdded = groups.UpdateGroupUserAdded
    type UpdateGroupUserLeave = groups.UpdateGroupUserLeave
    val UpdateGroupUserLeave = groups.UpdateGroupUserLeave
    type UpdateGroupUserKick = groups.UpdateGroupUserKick
    val UpdateGroupUserKick = groups.UpdateGroupUserKick
    type UpdateGroupMembersUpdate = groups.UpdateGroupMembersUpdate
    val UpdateGroupMembersUpdate = groups.UpdateGroupMembersUpdate
    type UpdateGroupTitleChanged = groups.UpdateGroupTitleChanged
    val UpdateGroupTitleChanged = groups.UpdateGroupTitleChanged
    type UpdateGroupAvatarChanged = groups.UpdateGroupAvatarChanged
    val UpdateGroupAvatarChanged = groups.UpdateGroupAvatarChanged
    type MessageState = conversations.MessageState.MessageState
    val MessageState = conversations.MessageState
    type HistoryMessage = conversations.HistoryMessage
    val HistoryMessage = conversations.HistoryMessage
    type RequestLoadHistory = conversations.RequestLoadHistory
    val RequestLoadHistory = conversations.RequestLoadHistory
    type ResponseLoadHistory = conversations.ResponseLoadHistory
    val ResponseLoadHistory = conversations.ResponseLoadHistory
    type Dialog = conversations.Dialog
    val Dialog = conversations.Dialog
    type RequestLoadDialogs = conversations.RequestLoadDialogs
    val RequestLoadDialogs = conversations.RequestLoadDialogs
    type ResponseLoadDialogs = conversations.ResponseLoadDialogs
    val ResponseLoadDialogs = conversations.ResponseLoadDialogs
    type UserKey = encryption.UserKey
    val UserKey = encryption.UserKey
    type PublicKey = encryption.PublicKey
    val PublicKey = encryption.PublicKey
    type UpdateNewDevice = encryption.UpdateNewDevice
    val UpdateNewDevice = encryption.UpdateNewDevice
    type UpdateRemovedDevice = encryption.UpdateRemovedDevice
    val UpdateRemovedDevice = encryption.UpdateRemovedDevice
    type PublicKeyRequest = encryption.PublicKeyRequest
    val PublicKeyRequest = encryption.PublicKeyRequest
    type RequestGetPublicKeys = encryption.RequestGetPublicKeys
    val RequestGetPublicKeys = encryption.RequestGetPublicKeys
    type ResponseGetPublicKeys = encryption.ResponseGetPublicKeys
    val ResponseGetPublicKeys = encryption.ResponseGetPublicKeys
    type RequestTyping = weak.RequestTyping
    val RequestTyping = weak.RequestTyping
    type RequestSetOnline = weak.RequestSetOnline
    val RequestSetOnline = weak.RequestSetOnline
    type UpdateTyping = weak.UpdateTyping
    val UpdateTyping = weak.UpdateTyping
    type UpdateUserOnline = weak.UpdateUserOnline
    val UpdateUserOnline = weak.UpdateUserOnline
    type UpdateUserOffline = weak.UpdateUserOffline
    val UpdateUserOffline = weak.UpdateUserOffline
    type UpdateUserLastSeen = weak.UpdateUserLastSeen
    val UpdateUserLastSeen = weak.UpdateUserLastSeen
    type UpdateGroupOnline = weak.UpdateGroupOnline
    val UpdateGroupOnline = weak.UpdateGroupOnline
    type FileLocation = files.FileLocation
    val FileLocation = files.FileLocation
    type AvatarImage = files.AvatarImage
    val AvatarImage = files.AvatarImage
    type Avatar = files.Avatar
    val Avatar = files.Avatar
    type FastThumb = files.FastThumb
    val FastThumb = files.FastThumb
    type RequestGetFile = files.RequestGetFile
    val RequestGetFile = files.RequestGetFile
    type ResponseGetFile = files.ResponseGetFile
    val ResponseGetFile = files.ResponseGetFile
    type UploadConfig = files.UploadConfig
    val UploadConfig = files.UploadConfig
    val RequestStartUpload = files.RequestStartUpload
    type ResponseStartUpload = files.ResponseStartUpload
    val ResponseStartUpload = files.ResponseStartUpload
    type RequestUploadPart = files.RequestUploadPart
    val RequestUploadPart = files.RequestUploadPart
    type RequestCompleteUpload = files.RequestCompleteUpload
    val RequestCompleteUpload = files.RequestCompleteUpload
    type ResponseCompleteUpload = files.ResponseCompleteUpload
    val ResponseCompleteUpload = files.ResponseCompleteUpload
    type RequestRegisterGooglePush = push.RequestRegisterGooglePush
    val RequestRegisterGooglePush = push.RequestRegisterGooglePush
    type RequestRegisterApplePush = push.RequestRegisterApplePush
    val RequestRegisterApplePush = push.RequestRegisterApplePush
    val RequestUnregisterPush = push.RequestUnregisterPush
    type PeerType = peers.PeerType.PeerType
    val PeerType = peers.PeerType
    type Peer = peers.Peer
    val Peer = peers.Peer
    type OutPeer = peers.OutPeer
    val OutPeer = peers.OutPeer
    type UserOutPeer = peers.UserOutPeer
    val UserOutPeer = peers.UserOutPeer
    type GroupOutPeer = peers.GroupOutPeer
    val GroupOutPeer = peers.GroupOutPeer
    type SeqUpdate = sequence.SeqUpdate
    type FatSeqUpdate = sequence.FatSeqUpdate
    type WeakUpdate = sequence.WeakUpdate
    val SeqUpdateTooLong = sequence.SeqUpdateTooLong
    val RequestGetState = sequence.RequestGetState
    type DifferenceUpdate = sequence.DifferenceUpdate
    val DifferenceUpdate = sequence.DifferenceUpdate
    type RequestGetDifference = sequence.RequestGetDifference
    val RequestGetDifference = sequence.RequestGetDifference
    type ResponseGetDifference = sequence.ResponseGetDifference
    val ResponseGetDifference = sequence.ResponseGetDifference
    type RequestSubscribeToOnline = sequence.RequestSubscribeToOnline
    val RequestSubscribeToOnline = sequence.RequestSubscribeToOnline
    type RequestSubscribeFromOnline = sequence.RequestSubscribeFromOnline
    val RequestSubscribeFromOnline = sequence.RequestSubscribeFromOnline
    type RequestSubscribeToGroupOnline = sequence.RequestSubscribeToGroupOnline
    val RequestSubscribeToGroupOnline = sequence.RequestSubscribeToGroupOnline
    type RequestSubscribeFromGroupOnline = sequence.RequestSubscribeFromGroupOnline
    val RequestSubscribeFromGroupOnline = sequence.RequestSubscribeFromGroupOnline
    val ResponseVoid = misc.ResponseVoid
    type ResponseSeq = misc.ResponseSeq
    val ResponseSeq = misc.ResponseSeq
    type ResponseSeqDate = misc.ResponseSeqDate
    val ResponseSeqDate = misc.ResponseSeqDate
    type Config = misc.Config
    val Config = misc.Config
    type UpdateConfig = misc.UpdateConfig
    val UpdateConfig = misc.UpdateConfig
  }
  class ParseException(partialMessage: Any) extends Exception
  trait UpdateBox
  trait Update
  trait RpcRequest
  trait RpcResponse
}
