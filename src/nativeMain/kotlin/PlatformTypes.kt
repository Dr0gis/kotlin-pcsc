/*
 * PlatformTypes.kt
 * Type aliases for Windows on Kotlin/Native.
 *
 * Copyright 2019-2021 Michael Farrell <micolous+git@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:OptIn(ExperimentalForeignApi::class)

package au.id.micolous.kotlin.pcsc

import au.id.micolous.kotlin.pcsc.internal.WCardBeginTransaction
import au.id.micolous.kotlin.pcsc.internal.WCardCancel
import au.id.micolous.kotlin.pcsc.internal.WCardDisconnect
import au.id.micolous.kotlin.pcsc.internal.WCardEndTransaction
import au.id.micolous.kotlin.pcsc.internal.WCardEstablishContext
import au.id.micolous.kotlin.pcsc.internal.WCardGetAttrib
import au.id.micolous.kotlin.pcsc.internal.WCardGetStatusChange
import au.id.micolous.kotlin.pcsc.internal.WCardIsValidContext
import au.id.micolous.kotlin.pcsc.internal.WCardListReaders
import au.id.micolous.kotlin.pcsc.internal.WCardReconnect
import au.id.micolous.kotlin.pcsc.internal.WCardReleaseContext
import au.id.micolous.kotlin.pcsc.internal.WCardStatus
import au.id.micolous.kotlin.pcsc.internal.WCardTransmit
import kotlinx.cinterop.ExperimentalForeignApi
import platform.windows.DWORD
import platform.windows.DWORDVar
import platform.windows.SCARDCONTEXT
import platform.windows.SCARDCONTEXTVar
import platform.windows.SCARDHANDLE
import platform.windows.SCARDHANDLEVar
import platform.windows.SCARD_ABSENT
import platform.windows.SCARD_EJECT_CARD
import platform.windows.SCARD_LEAVE_CARD
import platform.windows.SCARD_NEGOTIABLE
import platform.windows.SCARD_PCI_RAW
import platform.windows.SCARD_PCI_T0
import platform.windows.SCARD_PCI_T1
import platform.windows.SCARD_POWERED
import platform.windows.SCARD_PRESENT
import platform.windows.SCARD_PROTOCOL_RAW
import platform.windows.SCARD_PROTOCOL_T0
import platform.windows.SCARD_PROTOCOL_T1
import platform.windows.SCARD_PROTOCOL_UNDEFINED
import platform.windows.SCARD_READERSTATEA
import platform.windows.SCARD_RESET_CARD
import platform.windows.SCARD_SCOPE_SYSTEM
import platform.windows.SCARD_SCOPE_TERMINAL
import platform.windows.SCARD_SCOPE_USER
import platform.windows.SCARD_SHARE_DIRECT
import platform.windows.SCARD_SHARE_EXCLUSIVE
import platform.windows.SCARD_SHARE_SHARED
import platform.windows.SCARD_SPECIFIC
import platform.windows.SCARD_STATE_ATRMATCH
import platform.windows.SCARD_STATE_CHANGED
import platform.windows.SCARD_STATE_EMPTY
import platform.windows.SCARD_STATE_EXCLUSIVE
import platform.windows.SCARD_STATE_IGNORE
import platform.windows.SCARD_STATE_INUSE
import platform.windows.SCARD_STATE_MUTE
import platform.windows.SCARD_STATE_PRESENT
import platform.windows.SCARD_STATE_UNAVAILABLE
import platform.windows.SCARD_STATE_UNAWARE
import platform.windows.SCARD_STATE_UNKNOWN
import platform.windows.SCARD_STATE_UNPOWERED
import platform.windows.SCARD_SWALLOWED
import platform.windows.SCARD_UNKNOWN
import platform.windows.SCARD_UNPOWER_CARD

/*
 * This makes common Windows types (also used in pcsclite) look like what we get out of pcsclite.
 */
internal typealias DWORD = DWORD
internal typealias DWORDVar = DWORDVar
internal typealias SCARD_READERSTATE_A = SCARD_READERSTATEA

/*
 * These are required because Kotlin/Native's windows.def massively overreaches across the Win32
 * API, by inclusion of windows.h without any headerFilter.
 */
internal typealias SCARDHANDLE = SCARDHANDLE
internal typealias SCARDHANDLEVar = SCARDHANDLEVar
internal typealias SCARDCONTEXT = SCARDCONTEXT
internal typealias SCARDCONTEXTVar = SCARDCONTEXTVar

internal const val SCARD_PROTOCOL_T0 = SCARD_PROTOCOL_T0
internal const val SCARD_PROTOCOL_T1 = SCARD_PROTOCOL_T1
internal const val SCARD_PROTOCOL_RAW = SCARD_PROTOCOL_RAW
internal const val SCARD_PROTOCOL_UNDEFINED = SCARD_PROTOCOL_UNDEFINED

internal const val SCARD_SCOPE_USER = SCARD_SCOPE_USER
internal const val SCARD_SCOPE_TERMINAL = SCARD_SCOPE_TERMINAL
internal const val SCARD_SCOPE_SYSTEM = SCARD_SCOPE_SYSTEM
internal const val SCARD_SHARE_SHARED = SCARD_SHARE_SHARED
internal const val SCARD_SHARE_EXCLUSIVE = SCARD_SHARE_EXCLUSIVE
internal const val SCARD_SHARE_DIRECT = SCARD_SHARE_DIRECT

internal const val SCARD_LEAVE_CARD = SCARD_LEAVE_CARD
internal const val SCARD_RESET_CARD = SCARD_RESET_CARD
internal const val SCARD_UNPOWER_CARD = SCARD_UNPOWER_CARD
internal const val SCARD_EJECT_CARD = SCARD_EJECT_CARD

internal const val SCARD_NEGOTIABLE = SCARD_NEGOTIABLE
internal const val SCARD_POWERED = SCARD_POWERED
internal const val SCARD_SWALLOWED = SCARD_SWALLOWED
internal const val SCARD_PRESENT = SCARD_PRESENT
internal const val SCARD_ABSENT = SCARD_ABSENT
internal const val SCARD_UNKNOWN = SCARD_UNKNOWN
internal const val SCARD_SPECIFIC = SCARD_SPECIFIC

internal const val SCARD_STATE_UNAWARE = SCARD_STATE_UNAWARE
internal const val SCARD_STATE_IGNORE = SCARD_STATE_IGNORE
internal const val SCARD_STATE_CHANGED = SCARD_STATE_CHANGED
internal const val SCARD_STATE_UNKNOWN = SCARD_STATE_UNKNOWN
internal const val SCARD_STATE_UNAVAILABLE = SCARD_STATE_UNAVAILABLE
internal const val SCARD_STATE_EMPTY = SCARD_STATE_EMPTY
internal const val SCARD_STATE_PRESENT = SCARD_STATE_PRESENT
internal const val SCARD_STATE_ATRMATCH = SCARD_STATE_ATRMATCH
internal const val SCARD_STATE_EXCLUSIVE = SCARD_STATE_EXCLUSIVE
internal const val SCARD_STATE_INUSE = SCARD_STATE_INUSE
internal const val SCARD_STATE_MUTE = SCARD_STATE_MUTE
internal const val SCARD_STATE_UNPOWERED = SCARD_STATE_UNPOWERED

/*
 * These are wrapped as properties.
 */
internal val SCARD_PCI_T0 = SCARD_PCI_T0!!
internal val SCARD_PCI_T1 = SCARD_PCI_T1!!
internal val SCARD_PCI_RAW = SCARD_PCI_RAW!!

/*
 * These are also properties that point to functions. SCardConnect isn't here, because it doesn't
 * work right unless it's an actual function (due to string conversion).
 */
internal val SCardDisconnect = WCardDisconnect!!
internal val SCardGetAttrib = WCardGetAttrib!!
internal val SCardReconnect = WCardReconnect!!
internal val SCardTransmit = WCardTransmit!!
internal val SCardBeginTransaction = WCardBeginTransaction!!
internal val SCardEndTransaction = WCardEndTransaction!!
internal val SCardStatus = WCardStatus!!
internal val SCardReleaseContext = WCardReleaseContext!!
internal val SCardIsValidContext = WCardIsValidContext!!
internal val SCardCancel = WCardCancel!!
internal val SCardListReaders = WCardListReaders!!
internal val SCardEstablishContext = WCardEstablishContext!!
internal val SCardGetStatusChange = WCardGetStatusChange!!
